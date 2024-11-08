package com.mentorship.mentorship.controller;

import com.mentorship.mentorship.dto.TaskDto;
import com.mentorship.mentorship.exception.ApplicationExceptions;
import com.mentorship.mentorship.mapper.TaskMapper;
import com.mentorship.mentorship.service.TaskService;
import com.mentorship.mentorship.validator.TaskValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;
    private final TaskMapper taskMapper;

    @PostMapping
    public Mono<TaskDto> createTask(@RequestBody Mono<TaskDto> mono, @RequestParam Long userId) {
        return mono.transform(TaskValidator.validate())
                .as(taskDtoMono -> taskService.createTask(taskDtoMono, userId));
    }

    @PutMapping("/{id}")
    public Mono<TaskDto> updateTask(@PathVariable Long id, @RequestBody Mono<TaskDto> mono) {
        return mono.transform(TaskValidator.validate())
                .as(validTask -> taskService.updateTask(id, validTask))
                .switchIfEmpty(ApplicationExceptions.taskNotFound(id));

    }

    @GetMapping("/{id}")
    public Mono<TaskDto> getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id).switchIfEmpty(ApplicationExceptions.taskNotFound(id));
    }

    @GetMapping
    public Flux<TaskDto> getAllTasks(@RequestParam(defaultValue = "1") Integer page,
                                     @RequestParam(defaultValue = "3") Integer size) {
        return taskService.getAllTasks(page, size);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteTask(@PathVariable Long id) {
        return taskService.deleteTask(id)
                .filter(b -> b)
                .switchIfEmpty(ApplicationExceptions.taskNotFound(id))
                .then();
    }
}

