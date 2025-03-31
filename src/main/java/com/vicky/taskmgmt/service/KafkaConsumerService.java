package com.vicky.taskmgmt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    @Autowired
    private RedisService redisService;

    @KafkaListener(topics="task-topic", groupId = "task-group")
    public void listen(String message) {
        System.out.println("Kafka message received: " + message);
        String[] parts = message.split(":");
        String priorirty = parts[1];
        redisService.saveTaskPriorityCount(priorirty);
    }
    
}
