package com.mentorship.mentorship.controller;

import com.mentorship.mentorship.dto.TaskDto;
import com.mentorship.mentorship.mapper.TaskMapper;
import com.mentorship.mentorship.model.Task;
import com.mentorship.mentorship.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;
    private final TaskMapper taskMapper;

    @PostMapping
    public ResponseEntity<TaskDto> createTask(@RequestBody TaskDto taskDto, @RequestParam Long userId) {
        Task createdTask = taskService.createTask(taskMapper.toEntity(taskDto), userId);
        return new ResponseEntity<>(taskMapper.toDto(createdTask), HttpStatus.CREATED);
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<TaskDto> updateTask(@PathVariable Long taskId, @RequestBody TaskDto task) {
        Task updatedTask = taskService.updateTask(taskId, taskMapper.toEntity(task));
        return new ResponseEntity<>(taskMapper.toDto(updatedTask), HttpStatus.OK);
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<TaskDto> getTask(@PathVariable Long taskId) {
        Task task = taskService.getTaskById(taskId);
        return new ResponseEntity<>(taskMapper.toDto(task), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<TaskDto>> getAllTasks(Pageable pageable) {
        Page<Task> tasksPage = taskService.getAllTasks(pageable);
        Page<TaskDto> taskDtoPage = tasksPage.map(taskMapper::toDto);
        return new ResponseEntity<>(taskDtoPage, HttpStatus.OK);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long taskId) {
        taskService.deleteTask(taskId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

