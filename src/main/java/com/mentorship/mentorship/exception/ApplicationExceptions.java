package com.mentorship.mentorship.exception;

import reactor.core.publisher.Mono;

public class ApplicationExceptions {
    private ApplicationExceptions() {
    }

    public static <T> Mono<T> userNotFound(Long id) {
        return Mono.error(new UserNotFoundException(id));
    }

    public static <T> Mono<T> missingFirstName() {
        return Mono.error(new InvalidInputException("First name is required"));
    }

    public static <T> Mono<T> missingLastName() {
        return Mono.error(new InvalidInputException("Last name is required"));
    }

    public static <T> Mono<T> taskNotFound(Long id) {
        return Mono.error(new TaskNotFoundException(id));
    }

    public static <T> Mono<T> missingTitle() {
        return Mono.error(new InvalidInputException("Title is required"));
    }

    public static <T> Mono<T> missingOwnerId() {
        return Mono.error(new InvalidInputException("Owner ID is required"));
    }
}
