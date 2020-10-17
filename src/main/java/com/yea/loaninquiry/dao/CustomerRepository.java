package com.yea.loaninquiry.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yea.loaninquiry.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>{

}
