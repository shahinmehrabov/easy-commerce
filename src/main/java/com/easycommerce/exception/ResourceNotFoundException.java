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

    public ResourceNotFoundException(String resourceName1, String resourceName2, String resourceName3,
                                     String field2, long fieldID2, String field3, long fieldID3) {
        super(String.format("%s not found with %s %s: %d and %s %s: %d", resourceName1, resourceName2, field2, fieldID2, resourceName3, field3, fieldID3));
    }
}
