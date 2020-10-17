package com.yea.loaninquiry.controller.model;

import com.yea.loaninquiry.model.LoanStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CustomerResponse {
	
	private LoanStatus loanStatus;
	private int limit;
	private String message;	

}
