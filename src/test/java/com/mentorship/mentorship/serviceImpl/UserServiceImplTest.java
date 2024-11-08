package com.mentorship.mentorship.serviceImpl;

import com.mentorship.mentorship.dto.UserDto;
import com.mentorship.mentorship.mapper.UserMapper;
import com.mentorship.mentorship.model.User;
import com.mentorship.mentorship.repository.UserRepository;
import com.mentorship.mentorship.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private UserDto userDto;

    @BeforeEach
    void setup() {
        user = new User(1L, "Ahmed", "Faisal", 30, "Developer");
        userDto = new UserDto(1L, "Ahmed", "Faisal", 30, "Developer");
    }

    @Test
    void testCreateUser() {
        when(userMapper.toEntity(any(UserDto.class))).thenReturn(user);
        when(userRepository.save(any(User.class))).thenReturn(Mono.just(user));
        when(userMapper.toDto(any(User.class))).thenReturn(userDto);

        Mono<UserDto> result = userService.createUser(Mono.just(userDto));

        StepVerifier.create(result)
                .expectNext(userDto)
                .verifyComplete();

        verify(userMapper).toEntity(userDto);
        verify(userRepository).save(user);
        verify(userMapper).toDto(user);
    }

    @Test
    void testGetUserById() {
        when(userRepository.findById(1L)).thenReturn(Mono.just(user));
        when(userMapper.toDto(user)).thenReturn(userDto);

        Mono<UserDto> result = userService.getUserById(1L);

        StepVerifier.create(result)
                .expectNext(userDto)
                .verifyComplete();

        verify(userRepository).findById(1L);
        verify(userMapper).toDto(user);
    }

    @Test
    void testGetUserByIdNotFound() {
        when(userRepository.findById(1L)).thenReturn(Mono.empty());

        Mono<UserDto> result = userService.getUserById(1L);

        StepVerifier.create(result)
                .verifyComplete();

        verify(userRepository).findById(1L);
        verify(userMapper, never()).toDto(any());
    }

    @Test
    void testGetAllUsers() {
        when(userRepository.findBy(PageRequest.of(0, 3)))
                .thenReturn(Flux.just(user));
        when(userMapper.toDto(user)).thenReturn(userDto);

        Flux<UserDto> result = userService.getAllUsers(1, 3);

        StepVerifier.create(result)
                .expectNext(userDto)
                .verifyComplete();

        verify(userRepository).findBy(PageRequest.of(0, 3));
        verify(userMapper).toDto(user);
    }

    @Test
    void testUpdateUser() {
        when(userRepository.findById(1L)).thenReturn(Mono.just(user));
        when(userMapper.toEntity(userDto)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(Mono.just(user));
        when(userMapper.toDto(user)).thenReturn(userDto);

        Mono<UserDto> result = userService.updateUser(1L, Mono.just(userDto));

        StepVerifier.create(result)
                .expectNext(userDto)
                .verifyComplete();

        verify(userRepository).findById(1L);
        verify(userMapper).toEntity(userDto);
        verify(userRepository).save(user);
        verify(userMapper).toDto(user);
    }

    @Test
    void testDeleteUser() {
        when(userRepository.deleteUserById(1L)).thenReturn(Mono.just(true));
        Mono<Boolean> result = userService.deleteUser(1L);
        StepVerifier.create(result)
                .expectNext(true)
                .verifyComplete();
        verify(userRepository).deleteUserById(1L);
    }

    @Test
    void testDeleteUserNotFound() {
        when(userRepository.deleteUserById(1L)).thenReturn(Mono.just(false));
        Mono<Boolean> result = userService.deleteUser(1L);
        StepVerifier.create(result)
                .expectNext(false)
                .verifyComplete();
        verify(userRepository).deleteUserById(1L);
    }
}

