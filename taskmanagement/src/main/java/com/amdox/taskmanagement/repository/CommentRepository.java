package com.amdox.taskmanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.amdox.taskmanagement.model.Comment;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findByTaskId(Long taskId);
}

