package com.mentorship.mentorship.exception;

public class TaskNotFoundException extends RuntimeException {

    private static final String MESSAGE = "Task [id=%d] is not found";

    public TaskNotFoundException(Long id) {
        super(MESSAGE.formatted(id));
    }
}
