package com.mentorship.mentorship.exception;

public class UserNotFoundException extends RuntimeException {

    private static final String MESSAGE = "User [id=%d] is not found";

    public UserNotFoundException(Long id) {
        super(MESSAGE.formatted(id));
    }
}
