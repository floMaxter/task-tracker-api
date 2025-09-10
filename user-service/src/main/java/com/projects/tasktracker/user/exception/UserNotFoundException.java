package com.projects.tasktracker.user.exception;

public class UserNotFoundException extends GenericApplicationException {

    public UserNotFoundException(Long id) {
        super(String.format("User not found with id=%d", id));
    }

    public UserNotFoundException(String email) {
        super(String.format("User not found with email=%s", email));
    }
}
