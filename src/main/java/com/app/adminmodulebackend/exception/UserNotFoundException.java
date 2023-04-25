package com.app.adminmodulebackend.exception;

public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException(Long userId) {
        super("User with id " + userId + " not found");
    }

}
