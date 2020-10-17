package com.yea.loaninquiry.strategy;

import com.yea.loaninquiry.controller.model.CustomerResponse;

public interface LoanStrategy {
	
	public CustomerResponse calculate(int income);

}
