package com.yea.loaninquiry.strategy;

import com.yea.loaninquiry.controller.model.CustomerResponse;
import com.yea.loaninquiry.model.LoanStatus;

public class AcceptLoan implements LoanStrategy{

	@Override
	public CustomerResponse calculate(int income) {
		CustomerResponse customerResponse = new CustomerResponse();
		customerResponse.setLoanStatus(LoanStatus.OK);
		customerResponse.setLimit(10000);
		customerResponse.setMessage("Loan inquiry confirmed.");
		return customerResponse;
	}

}
