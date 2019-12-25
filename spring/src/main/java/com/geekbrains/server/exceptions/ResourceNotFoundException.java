package com.geekbrains.server.exceptions;

public class ResourceNotFoundException extends RestResourceException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}