package com.yea.service;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import com.yea.loaninquiry.controller.model.CustomerRequest;
import com.yea.loaninquiry.controller.model.CustomerResponse;
import com.yea.loaninquiry.model.Customer;
import com.yea.loaninquiry.model.LoanStatus;
import com.yea.loaninquiry.queue.QueueComponent;
import com.yea.loaninquiry.strategy.AcceptLoan;
import com.yea.loaninquiry.strategy.AcceptLoanWithFactor;
import com.yea.loaninquiry.strategy.LoanStrategy;
import com.yea.loaninquiry.strategy.RejectLoan;
import com.yea.loaninquiry.tool.EncryptUtils;

@Component
public class LoanInquiryService {

	private static final Logger logger = LogManager.getLogger(LoanInquiryService.class);

	@Autowired
	private LoanScoreInquiryService scoreInquiryService;

	@Autowired
	private QueueComponent smsQueue;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private TcknVerificationService tcknVerificationService;

	@Autowired
	AcceptLoan acceptLoan;

	@Autowired
	AcceptLoanWithFactor acceptLoanWithFactor;

	@Autowired
	RejectLoan rejectLoan;

	public CustomerResponse inquiry(CustomerRequest entity)
			throws SAXException, IOException, ParserConfigurationException {

		CustomerResponse customerResponse = new CustomerResponse();
		LoanStrategy loanStrategy;

		// TCKN verification ..
		if (!tcknVerificationService.verify(entity)) {
			customerResponse.setLoanStatus(LoanStatus.NOK);
			customerResponse.setMessage("TCKN verification failed, please review the information");
			customerResponse.setLimit(0);
			return customerResponse;
		}
		
		int score = scoreInquiryService.loanScoreInquiry(entity);

		if (score < 500) {
			loanStrategy = rejectLoan;
		} else if (score >= 500 && score < 1000) {
			loanStrategy = acceptLoan;
		} else {
			loanStrategy = acceptLoanWithFactor;
		}

		loanStrategy.calculate(entity.getIncome());

		saveCustomer(entity, customerResponse);
		smsQueue.addToQueue(entity);

		logger.info("Tckn: " + entity.getTckn() + ",Income:" + entity.getIncome() + ",Score:" + score + ",Limit:"
				+ customerResponse.getLimit() + ",Result:" + customerResponse.getMessage());
		return customerResponse;

	}

	private void saveCustomer(CustomerRequest entity, CustomerResponse customerResponse) {
		String encyrptedTckn = EncryptUtils.encode(entity.getTckn());
		String encyrptedMsisdn = EncryptUtils.encode(entity.getMsisdn());
		Customer customer = new Customer();
		customer.setTckn(encyrptedTckn);
		customer.setName(entity.getName());
		customer.setIncome(entity.getIncome());
		customer.setMsisdn(encyrptedMsisdn);
		customer.setLoanstatus(customerResponse.getLoanStatus());
		customer.setLimit(customerResponse.getLimit());

		try {
			customerService.save(customer);
			logger.info("Successfully saved customer info with tckn: " + entity.getTckn());
		} catch (Exception e) {
			logger.error("Error while saving customer info with tckn: " + entity.getTckn() + " - ", e);
		}
	}

}
