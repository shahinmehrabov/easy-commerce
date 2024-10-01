package com.easycommerce.exception;

public class ResourceAlreadyExistsException extends RuntimeException {
    public ResourceAlreadyExistsException(String resource, String name, String value) {
        super(String.format("%s with %s: %s already exists", resource, name, value));
    }
}
