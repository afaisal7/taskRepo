package com.mentorship.mentorship.service.impl;

import com.mentorship.mentorship.dto.UserDto;
import com.mentorship.mentorship.mapper.UserMapper;
import com.mentorship.mentorship.repository.UserRepository;
import com.mentorship.mentorship.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public Mono<UserDto> createUser(Mono<UserDto> mono) {
        return mono.map(userMapper::toEntity)
                .flatMap(this.userRepository::save)
                .map(userMapper::toDto);
    }

    @Override
    public Mono<UserDto> getUserById(Long id) {
        return this.userRepository.findById(id)
                .map(userMapper::toDto);
    }

    @Override
    public Flux<UserDto> getAllUsers(Integer page, Integer size) {
        return userRepository.findBy(PageRequest.of(page - 1, size)).map(userMapper::toDto);
    }

    @Override
    public Mono<UserDto> updateUser(Long id, Mono<UserDto> mono) {
        return this.userRepository.findById(id)
                .flatMap(entity -> mono)
                .map(userMapper::toEntity)
                .doOnNext(c -> c.setId(id))
                .flatMap(this.userRepository::save)
                .map(userMapper::toDto);
    }

    @Override
    public Mono<Boolean> deleteUser(Long id) {
        return userRepository.deleteUserById(id);
    }
}
