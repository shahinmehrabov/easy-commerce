package com.easycommerce.exception;

public class APIException extends RuntimeException {
    public APIException(String message) {
        super(message);
    }
}
