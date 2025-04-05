package com.vicky.taskmgmt.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    @Autowired
    private RedisService redisService;

    @KafkaListener(topics="task-topic", groupId = "task-group")
    public void listen(ConsumerRecord<String, String> record, Acknowledgment ack) {
        try{
            System.out.println("Kafka message received: " + record.value() + " from partition: " + record.partition());
            String[] parts = record.value().split(":");
            String priorirty = parts[1];
            redisService.saveTaskPriorityCount(priorirty, "increment");
            // Manually acknowledge the message
            ack.acknowledge();
        } catch (Exception e) {
            // Do not acknowledge, so the message can be retried
            System.err.println("Error processing message: " + e.getMessage());
        }
    }
    
}
