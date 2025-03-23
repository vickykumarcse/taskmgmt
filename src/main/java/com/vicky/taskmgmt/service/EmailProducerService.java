package com.vicky.taskmgmt.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vicky.taskmgmt.config.RabbitMqConfig;
import com.vicky.taskmgmt.dto.EmailRequest;

@Service
public class EmailProducerService {
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    public EmailProducerService(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendEmailRequest(String emailId, String subject, String message) {
        EmailRequest emailRequest = new EmailRequest(emailId, subject, message);
        try {
            String jsonMessage = objectMapper.writeValueAsString(emailRequest);
            System.out.println("Sending JSON: " + jsonMessage); // ✅ Log JSON before sending
        } catch (Exception e) {
            e.printStackTrace();
        }
        rabbitTemplate.convertAndSend(RabbitMqConfig.EMAIL_QUEUE_NAME, emailRequest);
        System.out.println("Email request sent to queue: "+ emailId);
    }
}
