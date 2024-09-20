package com.taskgpb.test.apiVkIntegration.Exceptions;

public class UserNotFoundException extends Exception {
    public UserNotFoundException(String message){
        super(message);
    }
}
