package com.amdox.taskmanagement.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amdox.taskmanagement.dto.UserTaskDTO;
import com.amdox.taskmanagement.model.User;
import com.amdox.taskmanagement.repository.TaskRepository;
import com.amdox.taskmanagement.repository.UserRepository;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin
public class AdminController {

    private final UserRepository userRepo;
    private final TaskRepository taskRepo;

    public AdminController(UserRepository userRepo,
            TaskRepository taskRepo) {
        this.userRepo = userRepo;
        this.taskRepo = taskRepo;
    }

    @GetMapping("/dashboard")
    @PreAuthorize("hasRole('ADMIN')")
    public Map<String, Object> stats() {

        Map<String, Object> map = new HashMap<>();

        map.put("users", userRepo.count());
        map.put("tasks", taskRepo.count());
        map.put("completed", taskRepo.findByStatus("DONE").size());

        return map;
    }

    @GetMapping("/tasks-by-user")
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserTaskDTO> tasksByUser() {

        List<User> users = userRepo.findAll();

        return users.stream()
                .map(user -> new UserTaskDTO(
                user.getId(),
                user.getName(),
                taskRepo.findByUser_Id(user.getId())
        ))
                .filter(dto -> !dto.getTasks().isEmpty())
                .collect(Collectors.toList());
    }
}
