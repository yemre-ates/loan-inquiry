package com.yea.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.yea.loaninquiry.controller.model.CustomerRequest;
import com.yea.loaninquiry.queue.QueueComponent;

@Component
public class SendSmsService {

	// Bu class'ın smsQueueu'yu okuyarak Sms gönderimi yapılacağı varsayılmıştır ..

	private static final Logger logger = LogManager.getLogger(SendSmsService.class);

	private final ExecutorService executor;

	private final QueueComponent queueComponent;
	private final Integer threadCount;

	public SendSmsService(@Value("${threadCount}") final Integer threadCount,
			@Autowired QueueComponent queueComponent) {
		this.threadCount = threadCount;
		this.queueComponent = queueComponent;
		this.executor = Executors.newFixedThreadPool(threadCount);
	}

	@PostConstruct
	public void init() {
		for (int i = 0; i < threadCount; i++) {
			executor.execute(new ProcessorThread());
		}
	}

	private class ProcessorThread implements Runnable {

		public ProcessorThread() {

		}

		@Override
		public void run() {

			while (true) {
				CustomerRequest customer = queueComponent.take();

				try {
					sendSms(customer);
				} catch (Exception e) {
					logger.error("ERROR while sending sms to " + customer.getMsisdn(), e);
				}
			}
		}
	}

	public void sendSms(CustomerRequest entity) {
		logger.info("Tckn: " + entity.getTckn() + "," + "Sms sent to:" + entity.getMsisdn());
		// sms sent ..
	}

}
