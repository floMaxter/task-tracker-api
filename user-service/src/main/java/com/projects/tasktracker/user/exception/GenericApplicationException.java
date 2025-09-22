package com.projects.tasktracker.user.exception;

public class GenericApplicationException extends RuntimeException {
    public GenericApplicationException(String message) {
        super(message);
    }

    public GenericApplicationException(Throwable cause) {
        super(cause);
    }

    public GenericApplicationException(String message, Throwable cause) {
        super(message, cause);
    }
}
