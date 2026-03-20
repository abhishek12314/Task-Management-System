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

import com.amdox.taskmanagement.dto.TaskDTO;
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
    public Task create(@RequestBody TaskDTO request) {
        return taskService.create(request);
    }

    @PutMapping("/{id}/{status}")
    public Task updateStatus(@PathVariable Long id, @PathVariable Task.TaskStatus status) {
        return taskService.updateStatus(id, status);
    }

    @GetMapping("/project/{projectId}")
    public List<Task> tasksForProject(@PathVariable Long projectId) {
        return taskService.tasksForProject(projectId);
    }

    @GetMapping("/user/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public List<Task> getTasksByUser(@PathVariable Long id) {
        return taskService.getTasksByUser(id);
    }
    

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable Long id) {
        taskService.delete(id);
    }
}
