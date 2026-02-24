package com.amdox.taskmanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.amdox.taskmanagement.model.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByAssignedUserId(Long id);

    List<Task> findByStatus(String status);

}