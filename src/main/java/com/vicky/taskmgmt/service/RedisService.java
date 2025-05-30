package com.vicky.taskmgmt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
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

    private String getRedisPriorityKey(String priority) {
        String key = null;
        if (priority.equalsIgnoreCase("Low")) {
            key = "task:low";
        } else if (priority.equalsIgnoreCase("Medium")) {
            key = "task:medium";
        } else if (priority.equalsIgnoreCase("High")) {
            key = "task:high";
        }
        return key;
    }

    public void saveTaskPriorityCount(String priority, String operation) {
        String key = getRedisPriorityKey(priority);
        if (key != null) {
            // Initialize key if it does not exist
            if (redisTemplate.opsForValue().get(key) == null || redisTemplate.opsForValue().get(key).equals("0")) {
                redisTemplate.opsForValue().set(key, 0);
            }
            if(operation.equals("increment")) {
                redisTemplate.opsForValue().increment(key);
            } else if(operation.equals("decrement")) {
                redisTemplate.opsForValue().decrement(key);
            }
        }
    }

    public void resetTaskPriorityCount() {
        redisTemplate.opsForValue().set("task:low", 0);
        redisTemplate.opsForValue().set("task:medium", 0);
        redisTemplate.opsForValue().set("task:high", 0);   
    }

    public Map<String, Integer> getPriorityCount() {
        Map<String, Integer> priorityCount = new HashMap<String, Integer>();
        priorityCount.put("task:low", (Integer)redisTemplate.opsForValue().get("task:low"));
        priorityCount.put("task:medium", (Integer)redisTemplate.opsForValue().get("task:medium"));
        priorityCount.put("task:high", (Integer)redisTemplate.opsForValue().get("task:high"));
        return priorityCount;
    }
}

