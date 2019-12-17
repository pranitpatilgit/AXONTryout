package com.pranit.core.exception;

public class ProductDeselectException extends RuntimeException {

    public ProductDeselectException(String message) {
        super(message);
    }

    public ProductDeselectException(String message, Throwable cause) {
        super(message, cause);
    }
}
