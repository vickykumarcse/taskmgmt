package com.vicky.taskmgmt.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.vicky.taskmgmt.model.Task;

public interface TaskRepository extends MongoRepository<Task, String> {
    List<Task> findByStatus(String status);
}
