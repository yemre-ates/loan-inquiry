package com.yea.service;

import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.yea.loaninquiry.controller.model.CustomerRequest;

@Component
public class LoanScoreInquiryService {
	
	private static final Logger logger = LogManager.getLogger(LoanScoreInquiryService.class);
	
	// Kredi skoru sorgulama servisi
	// Daha önceden yazıldığı varsayılıyor.
	// Servisten dönecek kredi skorunu burada random üretiyoruz..
	
	
	public int loanScoreInquiry(CustomerRequest entity) {
		int score = generateRandomScore();
		logger.info("Tckn: " + entity.getTckn() + " - Loan Score : " + score );
		return score;
	}
	
	private int generateRandomScore() {
		Random random = new Random();
		return random.nextInt(1500); 
	}

}
