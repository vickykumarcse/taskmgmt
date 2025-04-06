package com.vicky.taskmgmt.service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;
import com.rabbitmq.client.Channel;
import com.vicky.taskmgmt.config.RabbitMqConfig;
import com.vicky.taskmgmt.dto.EmailRequest;

@Service
public class EmailConsumerService {
    private static final Logger logger = LoggerFactory.getLogger(EmailConsumerService.class);
    private final EmailSenderService emailSenderService;

    public EmailConsumerService(EmailSenderService emailSenderService) {
        this.emailSenderService = emailSenderService;
    }

    @RabbitListener(queues = RabbitMqConfig.EMAIL_QUEUE_NAME, ackMode = "MANUAL", concurrency = RabbitMqConfig.PREFETCH_COUNT)
    public void consumeEmailRequest(EmailRequest emailRequest, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag, 
                                    Channel channel) {
        logger.info("Received email request: " + emailRequest.getEmailId());
        this.emailSenderService.sendEmail(emailRequest.getEmailId(), emailRequest.getSubject(), 
        emailRequest.getMessage(), deliveryTag, channel);
    }
}