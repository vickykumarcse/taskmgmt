package com.vicky.taskmgmt.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.vicky.taskmgmt.exception.ResourceNotFoundException;
import com.vicky.taskmgmt.model.Task;
import com.vicky.taskmgmt.service.TaskService;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    @Autowired
    private TaskService taskService;

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        Task createdTask = taskService.createTask(task);
        return new ResponseEntity<Task>(createdTask, HttpStatus.CREATED);
    }

    @PostMapping("/bulk/{count}")
    public ResponseEntity<String> createTasksInBulk(@PathVariable int count) {
        for(int i=0; i<count; i++) {
            Task task = new Task();
            task.setStatus("Completed");
            task.setPriority("Low");
            task.setTitle("Send Email: "+ i+1);
            task.setDescription("Bulk task created");
            taskService.createTask(task);
        }
        return new ResponseEntity<String>("Bulk task creation request submitted!", HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllTasks() {
        List<Task> tasks = taskService.getAllTasks();
        Map<String, Object> response = new HashMap<String, Object>();
        response.put("tasks", tasks);
        response.put("taskCount", tasks.size());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable String id) {
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
        return ResponseEntity.ok(updatedTask);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable String id) {
        if(taskService.taskExists(id)) {
            taskService.deleteTask(id);
            return ResponseEntity.ok(id);
        }
        logger.error("ResourceNotFoundException");
        throw new ResourceNotFoundException("Task not found with id " + id);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteAllTasks() {
        taskService.deleteAllTasks();
        return ResponseEntity.ok("All Tasks Deleted");
    }

    @GetMapping("/analytics")
    public ResponseEntity<Map<String, Integer>> getAnalytics() {
        return ResponseEntity.ok(taskService.getPriorityCount());
    }
}
