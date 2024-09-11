package com.easycommerce.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String resourceName, String field, String fieldName) {
        super(String.format("%s not found with %s: %s", resourceName, field, fieldName));
    }

    public ResourceNotFoundException(String resourceName, String field, long fieldID) {
        super(String.format("%s not found with %s: %d", resourceName, field, fieldID));
    }
}
