package com.geekbrains.server.exceptions;

public class ResourceAlreadyExistException extends RestResourceException {
    public ResourceAlreadyExistException(String message) {
        super(message);
    }
}
