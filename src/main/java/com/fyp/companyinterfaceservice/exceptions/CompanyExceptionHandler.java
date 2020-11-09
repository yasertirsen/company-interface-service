package com.fyp.companyinterfaceservice.exceptions;

import com.fyp.companyinterfaceservice.dto.HttpCustomResponse;
import feign.FeignException;
import feign.RetryableException;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;
import java.util.Objects;

import static com.fyp.companyinterfaceservice.constants.ErrorConstants.INTERNAL_SERVER_ERROR_MSG;
import static com.fyp.companyinterfaceservice.constants.ErrorConstants.INVALID_CREDENTIALS;
import static com.fyp.companyinterfaceservice.constants.ErrorConstants.INVALID_DATA_FORMAT;
import static com.fyp.companyinterfaceservice.constants.ErrorConstants.METHOD_IS_NOT_ALLOWED;
import static com.fyp.companyinterfaceservice.constants.ErrorConstants.NOT_ENOUGH_PERMISSION;
import static com.fyp.companyinterfaceservice.constants.ErrorConstants.SERVER_COULD_NOT_BE_REACH;
import static com.fyp.companyinterfaceservice.constants.ErrorConstants.UNEXPECTED_VALUE;
import static com.fyp.companyinterfaceservice.constants.ErrorConstants.USERNAME_OR_EMAIL_EXISTS;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.GATEWAY_TIMEOUT;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.METHOD_NOT_ALLOWED;
import static org.springframework.http.HttpStatus.NOT_IMPLEMENTED;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@RestControllerAdvice
public class CompanyExceptionHandler {


    @ExceptionHandler(FeignException.class)
    public ResponseEntity<HttpCustomResponse> handleFeignStatusException(FeignException e) {

        ResponseEntity<HttpCustomResponse> toReturn = illegalStateException();

        switch (e.status()) {
            case 400:
                toReturn = invalidDataFormat();
                break;
            case 401:
                toReturn = invalidCredentials();
                break;
            case 403:
                toReturn = accessDeniedException();
                break;
//            case 423:
//                toReturn = accountLockedException();
//                break;
            case 409:
                toReturn = usernameOrEmailExistsException();
                break;
            case 500:
                toReturn = internalServerErrorException();
        }
        return toReturn;
    }

    @ExceptionHandler(UsernameOrEmailExistsException.class)
    public ResponseEntity<HttpCustomResponse> usernameOrEmailExistsException() {
        return createHttpResponse(CONFLICT, USERNAME_OR_EMAIL_EXISTS);
    }

    @ExceptionHandler(RetryableException.class)
    public ResponseEntity<HttpCustomResponse> retryableException() {
        return createHttpResponse(GATEWAY_TIMEOUT, SERVER_COULD_NOT_BE_REACH);
    }


    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<HttpCustomResponse> illegalStateException() {
        return createHttpResponse(NOT_IMPLEMENTED, UNEXPECTED_VALUE);
    }

//    @ExceptionHandler(DisabledException.class)
//    public ResponseEntity<HttpCustomResponse> accountDisabledException() {
//        return createHttpResponse(FORBIDDEN, ACCOUNT_DISABLED);
//    }
//
//    @ExceptionHandler(LockedException.class)
//    public ResponseEntity<HttpCustomResponse> accountLockedException() {
//        return createHttpResponse(LOCKED, ACCOUNT_LOCKED);
//    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<HttpCustomResponse> accessDeniedException() {
        return createHttpResponse(FORBIDDEN, NOT_ENOUGH_PERMISSION);
    }

//    @ExceptionHandler(TokenExpiredException.class)
//    public ResponseEntity<HttpCustomResponse> tokenExpiredException(TokenExpiredException e) {
//        return createHttpResponse(UNAUTHORIZED, e.getMessage());
//    }


    @ExceptionHandler(InvalidDataFormatException.class)
    public ResponseEntity<HttpCustomResponse> invalidDataFormat() {
        return createHttpResponse(BAD_REQUEST, INVALID_DATA_FORMAT);
    }


    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<HttpCustomResponse> invalidCredentials() {
        return createHttpResponse(UNAUTHORIZED, INVALID_CREDENTIALS);
    }


    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<HttpCustomResponse> methodNotSupportedException(HttpRequestMethodNotSupportedException exception) {
        HttpMethod supportedMethod = Objects.requireNonNull(exception.getSupportedHttpMethods()).iterator().next();
        return createHttpResponse(METHOD_NOT_ALLOWED, String.format(METHOD_IS_NOT_ALLOWED, supportedMethod));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<HttpCustomResponse> internalServerErrorException() {
        return createHttpResponse(INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERROR_MSG);
    }


    private ResponseEntity<HttpCustomResponse> createHttpResponse(HttpStatus httpStatus, String message) {
        HttpCustomResponse httpCustomResponse = new HttpCustomResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(), message);

        return new ResponseEntity<>(httpCustomResponse, httpStatus);
    }

}
