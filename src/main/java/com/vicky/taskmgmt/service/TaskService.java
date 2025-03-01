package com.vicky.taskmgmt.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vicky.taskmgmt.exception.ResourceNotFoundException;
import com.vicky.taskmgmt.model.Task;
import com.vicky.taskmgmt.repository.TaskRepository;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public Task createTask(Task task) {
        return taskRepository.save(task); // Save a new task
    }

    public Task updateTask(String id, Task taskDetails) {
        // Retrieve the existing task by ID
        Task existingTask = taskRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Task not found with id " + id));

        // Update fields of the existing task with the details from the incoming taskDetails object
        existingTask.setTitle(taskDetails.getTitle());
        existingTask.setDescription(taskDetails.getDescription());
        existingTask.setStatus(taskDetails.getStatus());
        existingTask.setPriority(taskDetails.getPriority());
        existingTask.setDueDate(taskDetails.getDueDate());
        
        // Save the updated task back to the repository
        return taskRepository.save(existingTask);
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll(); // Retrieve all tasks
    }

    public List<Task> findByStatus(String status) {
        return taskRepository.findByStatus(status);
    }

    public Optional<Task> getTaskById(String id) {
        return taskRepository.findById(id); // Retrieve a task by ID
    }

    public boolean taskExists(String id) {
        return taskRepository.existsById(id); // Check if a task exists
    }

    public void deleteTask(String id) {
        taskRepository.deleteById(id); // Delete a task by ID
    }

}
