package com.yea.loaninquiry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan("com.kocfinans.loaninquiry")
@SpringBootApplication
public class LoanInquiryApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoanInquiryApplication.class, args);
	}

}
