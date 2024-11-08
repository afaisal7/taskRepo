package com.mentorship.mentorship.service.impl;

import com.mentorship.mentorship.dto.TaskDto;
import com.mentorship.mentorship.mapper.TaskMapper;
import com.mentorship.mentorship.mapper.UserMapper;
import com.mentorship.mentorship.repository.TaskRepository;
import com.mentorship.mentorship.repository.UserRepository;
import com.mentorship.mentorship.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TaskMapper taskMapper;
    private final UserMapper userMapper;

    @Override
    public Mono<TaskDto> createTask(Mono<TaskDto> taskDtoMono, Long userId) {
        return taskDtoMono.map(taskMapper::toEntity)
                .flatMap(taskRepository::save)
                .map(taskMapper::toDto);
    }

    @Override
    public Mono<TaskDto> getTaskById(Long taskId) {
        return taskRepository.findById(taskId)
                .flatMap(task -> {
                    return userRepository.findById(task.getOwnerId())
                            .map(user -> {
                                TaskDto taskDto = taskMapper.toDto(task);
                                taskDto.setOwner(userMapper.toDto(user));
                                return taskDto;
                            });
                });
    }

    @Override
    public Flux<TaskDto> getAllTasks(Integer page, Integer size) {
        return taskRepository.findBy(PageRequest.of(page - 1, size))
                .flatMap(task -> {
                    return userRepository.findById(task.getOwnerId())
                            .map(user -> {
                                TaskDto taskDto = taskMapper.toDto(task);
                                taskDto.setOwner(userMapper.toDto(user));
                                return taskDto;
                            });
                });
    }

    @Override
    public Mono<TaskDto> updateTask(Long id, Mono<TaskDto> mono) {
        return this.taskRepository.findById(id)
                .flatMap(entity -> mono)
                .map(taskMapper::toEntity)
                //.doOnNext(c -> c.setId(id))
                .flatMap(this.taskRepository::save)
                .map(taskMapper::toDto);
    }

    @Override
    public Mono<Boolean> deleteTask(Long id) {
        return taskRepository.deleteTaskById(id);
    }
}
