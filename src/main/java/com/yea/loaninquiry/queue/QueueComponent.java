package com.yea.loaninquiry.queue;

import java.util.concurrent.LinkedBlockingQueue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.yea.loaninquiry.controller.model.CustomerRequest;

@Component
public class QueueComponent {
	
	private static final Logger logger = LogManager.getLogger(QueueComponent.class);

	private final LinkedBlockingQueue<CustomerRequest> smsQueue;
	
	public QueueComponent(@Value("${sms.queueCapacity}") final Integer queueCapacity) {
		logger.info("Creating new Queue with capacity:"+queueCapacity);
		this.smsQueue = new LinkedBlockingQueue<>(queueCapacity);
		logger.info("Created new smsQueue succesfully");
	}
	
	public void addToQueue(CustomerRequest customer) {
		logger.info("Adding smsQueue - Tckn:"+customer.getTckn() +",Msisdn:" + customer.getMsisdn());
		smsQueue.add(customer);
	}
	
	public CustomerRequest take() {
		try {
			logger.info("Take from smsQueue");
			return this.smsQueue.take();
		} catch (InterruptedException e) {
			logger.error("<takeFromQueue> Unable to take message from the smsQueue -> ", e);
		}
		logger.warn("Take from smsQueue returned NULL!!!");
		return null;
	}
	
}
