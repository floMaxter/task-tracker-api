package com.projects.tasktracker.user.exception;

public class OutboxSerializationException extends GenericApplicationException {

  public OutboxSerializationException(String message) {
    super(message);
  }

  public OutboxSerializationException(Throwable cause) {
    super(cause);
  }

  public OutboxSerializationException(String message, Throwable cause) {
    super(message, cause);
  }
}
