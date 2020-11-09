package com.fyp.companyinterfaceservice.exceptions;

import java.net.ConnectException;

public class CoreApplicationException extends ConnectException {

    public CoreApplicationException(String message) {super(message);}
}
