package com.mentorship.mentorship.service;

import com.mentorship.mentorship.dto.UserDto;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {
    Mono<UserDto> createUser(Mono<UserDto> user);
    Mono<UserDto> getUserById(Long id);
    Flux<UserDto> getAllUsers(Integer page, Integer size);
    Mono<UserDto> updateUser(Long id, Mono<UserDto> mono);
    Mono<Boolean> deleteUser(Long id);
}