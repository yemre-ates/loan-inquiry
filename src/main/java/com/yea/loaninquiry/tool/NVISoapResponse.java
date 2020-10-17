package com.yea.loaninquiry.tool;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class NVISoapResponse {
	
	@Getter
	private int statusCode;
	
	@Getter
	private String result;	

}
