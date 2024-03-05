package com.example.security.error_class;

public class UserAlreadyRegisteredException extends RuntimeException {
    public UserAlreadyRegisteredException(String message){
        super(message);
    }
}