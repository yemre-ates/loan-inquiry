package com.yea.loaninquiry.controller;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;

import com.yea.loaninquiry.controller.model.CustomerRequest;
import com.yea.loaninquiry.controller.model.CustomerResponse;
import com.yea.service.LoanInquiryService;


@RestController
@RequestMapping(path = "/loanInquiry")
public class LoanInquiryController {

	private static final Logger logger = LogManager.getLogger(LoanInquiryController.class);
	@Autowired
	LoanInquiryService inquiryService;
	
	@RequestMapping(method = RequestMethod.POST)
    public CustomerResponse inquiry(@RequestBody CustomerRequest request) throws SAXException, IOException, ParserConfigurationException{
		logger.info("New request, Tckn is:" + request.getTckn());
		return inquiryService.inquiry(request);
    }
}
