package com.projects.tasktracker.auth.exception;

public class UserServiceException extends GeneralApplicationException {

    public UserServiceException(String message) {
        super(message);
    }

    public UserServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
