package com.amdox.taskmanagement.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.amdox.taskmanagement.dto.TaskRequest;
import com.amdox.taskmanagement.model.Task;
import com.amdox.taskmanagement.model.User;
import com.amdox.taskmanagement.repository.TaskRepository;
import com.amdox.taskmanagement.repository.UserRepository;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository,
            UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public Task create(TaskRequest req) {

        User user = userRepository.findById(req.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Task task = new Task();
        task.setTitle(req.getTitle());
        task.setDescription(req.getDescription());
        task.setPriority(req.getPriority());
        task.setStatus(req.getStatus());
        task.setDeadline(req.getDeadline());
        task.setAssignedUser(user);

        return taskRepository.save(task);
    }

    public Task updateStatus(Long id, String status) {

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        task.setStatus(status);

        return taskRepository.save(task);
    }

    public List<Task> tasksForUser(Long id) {
        return taskRepository.findByAssignedUserId(id);
    }

    public void delete(Long id) {
        taskRepository.deleteById(id);

    }
}
