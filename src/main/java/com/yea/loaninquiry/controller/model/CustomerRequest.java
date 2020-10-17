package com.yea.loaninquiry.controller.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CustomerRequest {
	
	private String tckn;
	private String name;
	private int income;
	private String msisdn;
	private int birthYear;

}
