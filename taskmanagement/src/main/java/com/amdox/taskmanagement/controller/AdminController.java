package com.amdox.taskmanagement.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amdox.taskmanagement.repository.TaskRepository;
import com.amdox.taskmanagement.repository.UserRepository;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final UserRepository userRepo;
    private final TaskRepository taskRepo;

    public AdminController(UserRepository userRepo,
                           TaskRepository taskRepo) {
        this.userRepo = userRepo;
        this.taskRepo = taskRepo;
    }

    @GetMapping("/dashboard")
    public Map<String,Object> stats() {

        Map<String,Object> map = new HashMap<>();

        map.put("users", userRepo.count());
        map.put("tasks", taskRepo.count());
        map.put("completed", taskRepo.findByStatus("DONE").size());

        return map;
    }
}
