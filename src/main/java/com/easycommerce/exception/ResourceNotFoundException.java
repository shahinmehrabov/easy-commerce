package com.easycommerce.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String resourceName, String field, String fieldName) {
        super(String.format("%s not found with %s: %s", resourceName, field, fieldName));
    }

    public ResourceNotFoundException(String resourceName, String field, long fieldId) {
        super(String.format("%s not found with %s: %d", resourceName, field, fieldId));
    }
}
