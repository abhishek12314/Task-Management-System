package com.amdox.taskmanagement.dto;

import java.util.List;

import com.amdox.taskmanagement.model.Task;

public class UserTaskDTO {

    private Long userId;
    private String userName;
    private List<Task> tasks;

    public UserTaskDTO(Long userId, String userName, List<Task> tasks) {
        this.userId = userId;
        this.userName = userName;
        this.tasks = tasks;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public List<Task> getTasks() {
        return tasks;
    }
}
