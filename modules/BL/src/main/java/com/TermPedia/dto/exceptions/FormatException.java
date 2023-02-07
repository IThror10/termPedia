package com.TermPedia.dto.exceptions;

public class FormatException extends ActionsException {
    public FormatException() { super(); }
    public FormatException(String message) { super(message); }
    public FormatException(String message, Throwable cause) { super(message, cause); }
    public FormatException(Throwable cause) { super(cause); }
}
