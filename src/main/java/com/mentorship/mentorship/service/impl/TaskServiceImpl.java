package com.mentorship.mentorship.service.impl;

import com.mentorship.mentorship.model.Task;
import com.mentorship.mentorship.model.User;
import com.mentorship.mentorship.repository.TaskRepository;
import com.mentorship.mentorship.repository.UserRepository;
import com.mentorship.mentorship.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    @Override
    public Task createTask(Task task, Long userId) {
        User owner = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        task.setOwner(owner);
        return taskRepository.save(task);
    }
    @Override
    public Task updateTask(Long taskId, Task updatedTask) {
        Task existingTask = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        existingTask.setTitle(updatedTask.getTitle());
        existingTask.setDescription(updatedTask.getDescription());
        existingTask.setStatus(updatedTask.getStatus());
        existingTask.setFromDate(updatedTask.getFromDate());
        existingTask.setToDate(updatedTask.getToDate());
        return taskRepository.save(existingTask);
    }
    @Override
    public Task getTaskById(Long taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));
    }
    @Override
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }
    @Override
    public void deleteTask(Long taskId) {
        taskRepository.deleteById(taskId);
    }
}
