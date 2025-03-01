package com.vicky.taskmgmt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void saveTask(String taskId, Object task) {
        redisTemplate.opsForValue().set(taskId, task, 10, TimeUnit.MINUTES); // Store task in Redis with TTL of 10 mins
    }

    public Object getTask(String taskId) {
        return redisTemplate.opsForValue().get(taskId); // Retrieve task from Redis
    }

    public void deleteTask(String taskId) {
        redisTemplate.delete(taskId); // Delete task from Redis
    }
}

