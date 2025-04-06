package com.vicky.taskmgmt.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.stereotype.Service;
import com.vicky.taskmgmt.exception.ResourceNotFoundException;
import com.vicky.taskmgmt.model.Task;
import com.vicky.taskmgmt.repository.TaskRepository;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private RedisService redisService;

    @Autowired
    private EmailProducerService emailProducerService;

    @Autowired
    private KafkaProducerService kafkaProducerService;

    private static final Logger logger = LoggerFactory.getLogger(TaskService.class);


    public Task createTask(Task task) {
        Task newTask = taskRepository.save(task); // Save a new task
        String emailId = newTask.getId() + "@task.com";
        String subject = "Task Created: " + newTask.getTitle();
        String message = "Your task is created successfully! \n" + newTask.getDescription();
        try {
            redisService.saveTask(newTask.getId(), newTask); // Store in Redis
        } catch (RedisConnectionFailureException e) {
            logger.error("RedisConnectionFailureException", e.getMessage(), e);
        }
        this.emailProducerService.sendEmailRequest(emailId, subject, message);
        String kafkaMessage = newTask.getId() + ":" + newTask.getPriority();
        this.kafkaProducerService.sendMessage("task-topic", newTask.getId(), kafkaMessage);
        return newTask;
    }

    public Task updateTask(String id, Task taskDetails) {
        // Retrieve the existing task by ID
        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id " + id));

        // Update fields of the existing task with the details from the incoming
        // taskDetails object
        existingTask.setTitle(taskDetails.getTitle());
        existingTask.setDescription(taskDetails.getDescription());
        existingTask.setStatus(taskDetails.getStatus());
        existingTask.setPriority(taskDetails.getPriority());
        existingTask.setDueDate(taskDetails.getDueDate());

        // Save the updated task back to the repository
        Task updatedTask = taskRepository.save(existingTask);
        redisService.saveTask(updatedTask.getId(), updatedTask); // Store in Redis
        return updatedTask;
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll(); // Retrieve all tasks
    }

    public List<Task> findByStatus(String status) {
        return taskRepository.findByStatus(status);
    }

    public Optional<Task> getTaskById(String id) {
        Object cachedTask = redisService.getTask(id);
        if (cachedTask != null) {
            return Optional.ofNullable((Task) cachedTask);
        }
        return taskRepository.findById(id); // Retrieve a task by ID
    }

    public boolean taskExists(String id) {
        return taskRepository.existsById(id); // Check if a task exists
    }

    public void deleteTask(String id) {
        Optional<Task> task = taskRepository.findById(id);
        if (task != null) {
            taskRepository.deleteById(id); // Delete a task by ID
            try {
                redisService.deleteTask(id); // Remove from Redis
                redisService.saveTaskPriorityCount(task.get().getPriority(), "decrement");
            } catch (RedisConnectionFailureException e) {
                logger.error("RedisConnectionFailureException", e.getMessage(), e);
            }
        }
    }

    public void deleteAllTasks() {
        taskRepository.deleteAll();
        redisService.resetTaskPriorityCount();
    }

    public Map<String, Integer> getPriorityCount() {
        return redisService.getPriorityCount();
    }

}