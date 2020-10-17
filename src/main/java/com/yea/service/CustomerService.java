package com.yea.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yea.loaninquiry.dao.CustomerRepository;
import com.yea.loaninquiry.model.Customer;

@Component
@Transactional
public class CustomerService {
	
	 @Autowired
	 private CustomerRepository customerRepo;
	 
	 public void save(Customer entity) {
		 customerRepo.save(entity);		 
	 }
}
