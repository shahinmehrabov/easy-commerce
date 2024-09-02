package com.easycommerce.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class APIException extends RuntimeException {

    public APIException(String message) {
        super(message);
    }
}
