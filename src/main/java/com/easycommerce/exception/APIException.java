package com.easycommerce.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class APIException extends RuntimeException {
    public APIException(String message) {
        super(message);
    }

    public APIException(String resource, String name) {
        super(String.format("%s with the name '%s' already exists", resource, name));
    }
}
