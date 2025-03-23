package com.vicky.taskmgmt.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;
import com.rabbitmq.client.Channel;
import com.vicky.taskmgmt.config.RabbitMqConfig;
import com.vicky.taskmgmt.dto.EmailRequest;

@Service
public class EmailConsumerService {
    private final EmailSenderService emailSenderService;

    public EmailConsumerService(EmailSenderService emailSenderService) {
        this.emailSenderService = emailSenderService;
    }

    @RabbitListener(queues = RabbitMqConfig.EMAIL_QUEUE_NAME, ackMode = "MANUAL")
    public void consumeEmailRequest(EmailRequest emailRequest, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag, 
                                    Channel channel) {
        System.out.println("Received email request: " + emailRequest.getEmailId());
        this.emailSenderService.sendEmail(emailRequest.getEmailId(), emailRequest.getSubject(), 
        emailRequest.getMessage(), deliveryTag, channel);
    }
}