package com.fyp.companyinterfaceservice.exceptions;

public class UsernameOrEmailExistsException extends Exception {
    public UsernameOrEmailExistsException(String message) {
        super(message);
    }
}
