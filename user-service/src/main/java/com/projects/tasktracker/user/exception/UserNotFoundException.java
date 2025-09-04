package com.projects.tasktracker.user.exception;

public class UserNotFoundException extends GenericApplicationException {

    public UserNotFoundException(Long id) {
        super(String.format("User with id=%d does not exist", id));
    }
}
