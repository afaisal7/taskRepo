package com.mentorship.mentorship.controller;

import com.mentorship.mentorship.dto.UserDto;
import com.mentorship.mentorship.exception.ApplicationExceptions;
import com.mentorship.mentorship.mapper.UserMapper;
import com.mentorship.mentorship.service.UserService;
import com.mentorship.mentorship.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping
    public Mono<UserDto> createUser(@RequestBody Mono<UserDto> mono) {
        return mono.transform(UserValidator.validate())
                .as(userService::createUser);
    }

    @GetMapping("/{id}")
    public Mono<UserDto> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .switchIfEmpty(ApplicationExceptions.userNotFound(id));
    }

    @GetMapping
    public Flux<UserDto> getAllUsers(@RequestParam(defaultValue = "1") Integer page,
                                     @RequestParam(defaultValue = "3") Integer size) {
        return userService.getAllUsers(page, size);
    }

    @PutMapping("/{id}")
    public Mono<UserDto> updateUser(@PathVariable Long id, @RequestBody Mono<UserDto> userDto) {
        return userDto.transform(UserValidator.validate())
                .as(validReq -> userService.updateUser(id, validReq))
                .switchIfEmpty(ApplicationExceptions.userNotFound(id));
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id)
                .filter(b -> b)
                .switchIfEmpty(ApplicationExceptions.userNotFound(id))
                .then();
    }
}
