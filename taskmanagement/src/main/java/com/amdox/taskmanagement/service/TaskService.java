package com.amdox.taskmanagement.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.amdox.taskmanagement.dto.TaskDTO;
import com.amdox.taskmanagement.model.Project;
import com.amdox.taskmanagement.model.Task;
import com.amdox.taskmanagement.model.Task.TaskStatus;
import com.amdox.taskmanagement.model.User;
import com.amdox.taskmanagement.repository.ProjectRepository;
import com.amdox.taskmanagement.repository.TaskRepository;
import com.amdox.taskmanagement.repository.UserRepository;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    public TaskService(TaskRepository taskRepository,
            UserRepository userRepository,
            ProjectRepository projectRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
    }

    public Task create(TaskDTO req) {

        User user = userRepository.findById(req.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Project project = projectRepository.findById(req.getProjectId())
                .orElseThrow(() -> new RuntimeException("Project not found"));

        Task task = new Task();
        task.setTitle(req.getTitle());
        task.setDescription(req.getDescription());
        task.setPriority(req.getPriority());
        task.setStatus(req.getStatus());
        task.setDeadline(req.getDeadline());
        task.setUser(user);
        task.setProject(project);

        return taskRepository.save(task);
    }

    public Task updateStatus(Long id, TaskStatus status) {

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        task.setStatus(status);

        return taskRepository.save(task);
    }

    public List<Task> tasksForUser(Long id) {
        return taskRepository.findByUser_Id(id);
    }

    public List<Task> tasksForProject(Long projectId) {
        return taskRepository.findByProjectId(projectId);
    }

    public void delete(Long id) {
        taskRepository.deleteById(id);

    }

    public List<Task> getTasksByUser(Long userId) {
        return taskRepository.findByUser_Id(userId);
    }

    public Task updateStatus(Long id, String status) {
        throw new UnsupportedOperationException("Unimplemented method 'updateStatus'");
    }
}
