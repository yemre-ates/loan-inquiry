package com.yea.loaninquiry;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import com.yea.loaninquiry.LoanInquiryApplication;
import com.yea.loaninquiry.controller.model.CustomerRequest;
import com.yea.loaninquiry.controller.model.CustomerResponse;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LoanInquiryApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoanInquiryApplicationControllerTests {
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@LocalServerPort
	private int port;
	
	private String getRootUrl() {
		return "http://localhost:" + port;
	}

	@Test
	public void contextLoads() {

	}

	@Test
	public void testCreateEmployee() {
		CustomerRequest request = new CustomerRequest();
		request.setBirthYear(1993);
		request.setIncome(10000);
		request.setMsisdn("555666311");
		request.setName("TestName TestSurname");
		request.setTckn("13547851247");
		
		restTemplate.put(getRootUrl() + "/loanInquiry", request);

		CustomerResponse response = restTemplate.getForObject(getRootUrl() + "/loanInquiry", CustomerResponse.class);
		assertNotNull(response);
	}

}
