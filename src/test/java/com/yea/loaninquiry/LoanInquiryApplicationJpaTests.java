package com.yea.loaninquiry;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.yea.loaninquiry.dao.CustomerRepository;
import com.yea.loaninquiry.model.Customer;
import com.yea.loaninquiry.model.LoanStatus;

@DataJpaTest	
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class LoanInquiryApplicationJpaTests {
	
	private CustomerRepository customerRepo;
	
	
	@Autowired
	public void setCustomerRepo(CustomerRepository customerRepo) {
		this.customerRepo = customerRepo;
	}

	@Test
	public void addCustomer() {
		Customer customer = new Customer();
		customer.setIncome(5000);
		customer.setLimit(10000);
		customer.setLoanstatus(LoanStatus.OK);
		customer.setMsisdn("5556662211");
		customer.setName("TestName TestSurname");
		customer.setTckn("95664125474");
		
		customerRepo.save(customer);
		java.util.Optional<Customer> findCustomer = customerRepo.findById(customer.getId());
		assertTrue(findCustomer.isPresent());
	}

}
