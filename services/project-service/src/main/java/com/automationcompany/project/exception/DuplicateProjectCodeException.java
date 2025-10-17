package com.automationcompany.project.exception;

public class DuplicateProjectCodeException extends RuntimeException {
    public DuplicateProjectCodeException(String message) {
        super(message);
    }
}