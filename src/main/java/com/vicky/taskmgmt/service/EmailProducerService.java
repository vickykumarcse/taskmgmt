package com.vicky.taskmgmt.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vicky.taskmgmt.config.RabbitMqConfig;
import com.vicky.taskmgmt.dto.EmailRequest;

@Service
public class EmailProducerService {
    private static final Logger logger = LoggerFactory.getLogger(EmailProducerService.class);

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
            logger.info("Sending JSON: " + jsonMessage);// ✅ Log JSON before sending
        } catch (Exception e) {
            e.printStackTrace();
        }
        rabbitTemplate.convertAndSend(RabbitMqConfig.EMAIL_QUEUE_NAME, emailRequest);
        logger.info("Email request sent to queue: "+ emailId);
    }
}
