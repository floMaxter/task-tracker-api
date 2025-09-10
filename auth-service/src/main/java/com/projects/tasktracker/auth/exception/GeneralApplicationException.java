package com.projects.tasktracker.auth.exception;

public class GeneralApplicationException extends RuntimeException {
    public GeneralApplicationException(String message) {
        super(message);
    }

  public GeneralApplicationException(String message, Throwable cause) {
    super(message, cause);
  }
}
