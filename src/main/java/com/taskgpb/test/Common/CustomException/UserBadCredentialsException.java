package com.taskgpb.test.Common.CustomException;

public class UserBadCredentialsException extends Exception {
    public UserBadCredentialsException(String message) {
        super(message);
    }

}
