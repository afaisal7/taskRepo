package com.mentorship.mentorship.service;

import com.mentorship.mentorship.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TaskService {
    Task createTask(Task task, Long userId);

    Task updateTask(Long taskId, Task updatedTask);

    Task getTaskById(Long taskId);

    Page<Task> getAllTasks(Pageable pageable);

    void deleteTask(Long taskId);
}
