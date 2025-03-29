package com.vicky.taskmgmt;

import org.springframework.context.ApplicationContext;

import com.vicky.taskmgmt.service.EmailSenderService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TaskManagementSystemApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(TaskManagementSystemApplication.class, args);
		
		EmailSenderService emailSenderService = context.getBean(EmailSenderService.class);
		
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutting down thread pool...");
            emailSenderService.shutdownExecutor();
        }));
	}
}
