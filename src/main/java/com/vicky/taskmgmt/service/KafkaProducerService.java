package com.vicky.taskmgmt.service;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {
    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerService.class);

    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaProducerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String topic, String key, String message) {
        ProducerRecord<String, String> record = new ProducerRecord<String, String>(topic, key, message);
        kafkaTemplate.send(record).whenComplete((result, ex) -> {
            if(ex == null) {
                logger.info("Kafka mesage sent: " + message);
            } else {
                logger.error("Unable to send message=[" + message + "] due to: " + ex.getMessage());
            }
        });
    }
}
