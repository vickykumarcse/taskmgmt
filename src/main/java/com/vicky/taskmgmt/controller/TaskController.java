package com.vicky.taskmgmt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.vicky.taskmgmt.exception.ResourceNotFoundException;
import com.vicky.taskmgmt.model.Task;
import com.vicky.taskmgmt.service.RedisService;
import com.vicky.taskmgmt.service.TaskService;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private static final Logger logger = LogManager.getLogger(TaskController.class);

    @Autowired
    private TaskService taskService;

    @Autowired
    private RedisService redisService;

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        Task createdTask = taskService.createTask(task);
        try {
            redisService.saveTask(createdTask.getId(), createdTask); // Store in Redis
        } catch(RedisConnectionFailureException e) {
            logger.error("RedisConnectionFailureException", e.getMessage(), e);
        }
        return new ResponseEntity<Task>(createdTask, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task> tasks = taskService.getAllTasks();
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable String id) {
        Object cachedTask = redisService.getTask(id);
        if (cachedTask != null) {
            return ResponseEntity.ok((Task) cachedTask);
        }
        try {
            Task task = taskService.getTaskById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id " + id));
            return ResponseEntity.ok(task);
        } catch (ResourceNotFoundException e) {
            logger.error("Error fetching task with id {}: {}", id, e.getMessage());
            throw e; // Rethrow the exception if you want to propagate it further
        } catch (Exception e) {
            logger.error("Unexpected error occurred while fetching task with id {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("An unexpected error occurred", e);
        }
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Task>> findByStatus(@PathVariable String status) {
        List<Task> tasks = taskService.findByStatus(status);
        return ResponseEntity.ok(tasks);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable String id, @RequestBody Task taskDetails) {
        Task updatedTask = taskService.updateTask(id, taskDetails);
        redisService.saveTask(updatedTask.getId(), updatedTask); // Store in Redis
        return ResponseEntity.ok(updatedTask);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable String id) {
        if(taskService.taskExists(id)) {
            taskService.deleteTask(id);
            redisService.deleteTask(id); // Remove from Redis
            return ResponseEntity.ok(id);
        }
        logger.error("ResourceNotFoundException");
        throw new ResourceNotFoundException("Task not found with id " + id);
    }
}
