package com.TermPedia.dto.exceptions;

public class DataConflictException extends ActionsException {
    public DataConflictException() { super(); }
    public DataConflictException(String message) { super(message); }
    public DataConflictException(String message, Throwable cause) { super(message, cause); }
    public DataConflictException(Throwable cause) { super(cause); }
}
