package com.mentorship.mentorship.validator;

import com.mentorship.mentorship.dto.TaskDto;
import com.mentorship.mentorship.exception.ApplicationExceptions;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public class TaskValidator {
    public static UnaryOperator<Mono<TaskDto>> validate() {
        return mono -> mono.filter((hasTitle()))
                .switchIfEmpty(ApplicationExceptions.missingTitle())
                .filter(hasOwnerId())
                .switchIfEmpty(ApplicationExceptions.missingOwnerId());
    }

    private static Predicate<TaskDto> hasTitle() {
        return dto -> Objects.nonNull(dto.getTitle());
    }

    private static Predicate<TaskDto> hasOwnerId() {
        return dto -> Objects.nonNull(dto.getOwnerId());
    }
}
