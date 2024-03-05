package com.example.security.error_class;

public class InvalidAuthTokenException extends RuntimeException {
    public InvalidAuthTokenException(String message){
        super(message);
    }
}