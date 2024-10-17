package com.mentorship.mentorship.service.impl;

import com.mentorship.mentorship.exception.ResourceNotFoundException;
import com.mentorship.mentorship.mapper.TaskMapper;
import com.mentorship.mentorship.model.Task;
import com.mentorship.mentorship.model.User;
import com.mentorship.mentorship.repository.TaskRepository;
import com.mentorship.mentorship.repository.UserRepository;
import com.mentorship.mentorship.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TaskMapper taskMapper;

    @Override
    public Task createTask(Task task, Long userId) {
        User owner = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        task.setOwner(owner);
        return taskRepository.save(task);
    }

    @Override
    public Task updateTask(Long taskId, Task updatedTask) {
        Task existingTask = getTaskById(taskId);
        taskMapper.mapUserForUpdate(existingTask, updatedTask);
        return taskRepository.save(existingTask);
    }

    @Override
    public Task getTaskById(Long taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + taskId));
    }

    @Override
    public Page<Task> getAllTasks(Pageable pageable) {
        return taskRepository.findAll(pageable);
    }

    @Override
    public void deleteTask(Long taskId) {
        taskRepository.deleteById(taskId);
    }
}
