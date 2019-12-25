package com.geekbrains.server.exceptions;

public class RestResourceException extends RuntimeException {
    public RestResourceException(String message) {
        super(message);
    }
}
