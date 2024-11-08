package com.mentorship.mentorship.validator;

import com.mentorship.mentorship.dto.UserDto;
import com.mentorship.mentorship.exception.ApplicationExceptions;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public class UserValidator {
    public static UnaryOperator<Mono<UserDto>> validate() {
        return mono -> mono.filter(hasFirstName())
                .switchIfEmpty(ApplicationExceptions.missingFirstName())
                .filter(hasLastName())
                .switchIfEmpty(ApplicationExceptions.missingLastName());
    }
    private static Predicate<UserDto> hasFirstName() {
        return dto -> Objects.nonNull(dto.getFirstName());
    }
    private static Predicate<UserDto> hasLastName() {
        return dto -> Objects.nonNull(dto.getLastName());
    }
}
