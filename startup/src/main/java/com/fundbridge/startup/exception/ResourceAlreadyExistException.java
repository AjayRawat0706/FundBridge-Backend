package com.fundbridge.startup.exception;

public class ResourceAlreadyExistException extends RuntimeException {
    public ResourceAlreadyExistException(String message) {
        super(message);
    }
}
