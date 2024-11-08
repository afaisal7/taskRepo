package com.mentorship.mentorship.service;

import com.mentorship.mentorship.dto.TaskDto;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TaskService {
    Mono<TaskDto> createTask(Mono<TaskDto> taskDtoMono, Long userId);

    Mono<TaskDto> getTaskById(Long taskId);

    Flux<TaskDto> getAllTasks(Integer page, Integer size);

    Mono<TaskDto> updateTask(Long id, Mono<TaskDto> mono);

    Mono<Boolean> deleteTask(Long taskId);
}
