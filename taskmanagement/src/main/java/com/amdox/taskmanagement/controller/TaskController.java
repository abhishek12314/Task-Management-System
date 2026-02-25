package com.amdox.taskmanagement.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amdox.taskmanagement.dto.TaskRequest;
import com.amdox.taskmanagement.model.Task;
import com.amdox.taskmanagement.service.TaskService;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public Task create(@RequestBody TaskRequest request) {
        return taskService.create(request);
    }

    @PutMapping("/{id}/{status}")
    public Task updateStatus(@PathVariable Long id, @PathVariable String status) {
        return taskService.updateStatus(id, status);
    }

    @GetMapping("/user/{id}")
    public List<Task> tasksForUser(@PathVariable Long id) {
        return taskService.tasksForUser(id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable Long id) {
        taskService.delete(id);
    }
}
