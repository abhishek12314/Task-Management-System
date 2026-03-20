package com.amdox.taskmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.amdox.taskmanagement.model.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}