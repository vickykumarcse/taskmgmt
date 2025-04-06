package com.vicky.taskmgmt;

import org.springframework.context.ApplicationContext;

import com.vicky.taskmgmt.service.EmailSenderService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TaskManagementSystemApplication {
    private static final Logger logger = LoggerFactory.getLogger(TaskManagementSystemApplication.class);

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(TaskManagementSystemApplication.class, args);
		
		EmailSenderService emailSenderService = context.getBean(EmailSenderService.class);
		
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			logger.info("Shutting down thread pool...");
            emailSenderService.shutdownExecutor();
        }));
	}
}
