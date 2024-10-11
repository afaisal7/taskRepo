package com.mentorship.mentorship.repository;

import com.mentorship.mentorship.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {}
