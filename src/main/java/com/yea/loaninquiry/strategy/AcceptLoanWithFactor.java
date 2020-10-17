package com.yea.loaninquiry.strategy;

import org.springframework.beans.factory.annotation.Value;

import com.yea.loaninquiry.controller.model.CustomerResponse;
import com.yea.loaninquiry.model.LoanStatus;

public class AcceptLoanWithFactor implements LoanStrategy {
	
	private final int limitFactor;
	
	public AcceptLoanWithFactor(@Value("${limitFactor}") final int limitFactor) {
			this.limitFactor = limitFactor;
	}

	@Override
	public CustomerResponse calculate(int income) {
	CustomerResponse customerResponse = new CustomerResponse();
	customerResponse.setLoanStatus(LoanStatus.OK);
	customerResponse.setLimit(limitFactor * income);
	customerResponse.setMessage("Loan inquiry confirmed.");
	return customerResponse;
	}

}
