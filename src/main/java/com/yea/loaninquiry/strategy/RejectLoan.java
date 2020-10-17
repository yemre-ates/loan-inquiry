package com.yea.loaninquiry.strategy;

import com.yea.loaninquiry.controller.model.CustomerResponse;
import com.yea.loaninquiry.model.LoanStatus;

public class RejectLoan implements LoanStrategy{

	@Override
	public CustomerResponse calculate(int income) {
		CustomerResponse customerResponse = new CustomerResponse();
		customerResponse.setLoanStatus(LoanStatus.NOK);
		customerResponse.setLimit(0);
		customerResponse.setMessage("Loan inquiry rejected.");
		return customerResponse;
	}

}
