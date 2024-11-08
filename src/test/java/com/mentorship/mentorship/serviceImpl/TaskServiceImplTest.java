package com.mentorship.mentorship.serviceImpl;

import com.mentorship.mentorship.dto.TaskDto;
import com.mentorship.mentorship.dto.UserDto;
import com.mentorship.mentorship.mapper.TaskMapper;
import com.mentorship.mentorship.mapper.UserMapper;
import com.mentorship.mentorship.model.Task;
import com.mentorship.mentorship.model.User;
import com.mentorship.mentorship.repository.TaskRepository;
import com.mentorship.mentorship.repository.UserRepository;
import com.mentorship.mentorship.service.impl.TaskServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TaskMapper taskMapper;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private TaskServiceImpl taskService;

    @Test
    void testCreateTask() {
        TaskDto taskDto = new TaskDto(null, "Test Task", "A sample task description", "PENDING",
                LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(2),
                1L, null);

        Task taskEntity = new Task(); // Task entity with relevant fields
        when(taskMapper.toEntity(any(TaskDto.class))).thenReturn(taskEntity);
        when(taskRepository.save(any(Task.class))).thenReturn(Mono.just(taskEntity));
        when(taskMapper.toDto(any(Task.class))).thenReturn(taskDto);

        Mono<TaskDto> result = taskService.createTask(Mono.just(taskDto), 1L);

        StepVerifier.create(result)
                .expectNextMatches(dto -> dto.getTitle().equals("Test Task") && dto.getOwnerId() == 1L)
                .verifyComplete();
    }

    @Test
    void testGetTaskById() {
        Long taskId = 1L;
        Task taskEntity = new Task();
        taskEntity.setOwnerId(1L);
        TaskDto taskDto = new TaskDto(taskId, "Test Task", "A sample task description", "PENDING",
                LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(2),
                1L, null);
        User userEntity = new User(); // Mock user entity

        when(taskRepository.findById(taskId)).thenReturn(Mono.just(taskEntity));
        when(userRepository.findById(taskEntity.getOwnerId())).thenReturn(Mono.just(userEntity));
        when(taskMapper.toDto(any(Task.class))).thenReturn(taskDto);
        when(userMapper.toDto(any(User.class))).thenReturn(new UserDto(1L, "Ahmed", "Faisal", 35, "Developer"));

        Mono<TaskDto> result = taskService.getTaskById(taskId);

        StepVerifier.create(result)
                .expectNextMatches(dto -> dto.getId().equals(taskId) && dto.getOwner().getFirstName().equals("Ahmed"))
                .verifyComplete();
    }

    @Test
    void testDeleteTask() {
        Long taskId = 1L;
        when(taskRepository.deleteTaskById(taskId)).thenReturn(Mono.just(true));

        Mono<Boolean> result = taskService.deleteTask(taskId);

        StepVerifier.create(result)
                .expectNext(true)
                .verifyComplete();
    }
}

