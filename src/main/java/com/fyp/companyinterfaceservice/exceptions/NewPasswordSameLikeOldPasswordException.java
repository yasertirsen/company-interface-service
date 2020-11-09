package com.fyp.companyinterfaceservice.exceptions;

public class NewPasswordSameLikeOldPasswordException extends Exception {

    public NewPasswordSameLikeOldPasswordException(String message) {
        super(message);
    }
}
