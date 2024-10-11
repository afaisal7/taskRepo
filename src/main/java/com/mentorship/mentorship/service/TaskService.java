package com.mentorship.mentorship.service;

import com.mentorship.mentorship.model.Task;

import java.util.List;

public interface TaskService {
    Task createTask(Task task, Long userId);

    Task updateTask(Long taskId, Task updatedTask);

    Task getTaskById(Long taskId);

    List<Task> getAllTasks();

    void deleteTask(Long taskId);
}
