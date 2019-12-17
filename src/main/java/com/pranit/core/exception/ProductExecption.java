package com.pranit.core.exception;

public class ProductExecption extends RuntimeException {

    public ProductExecption(String message) {
        super(message);
    }

    public ProductExecption(String message, Throwable cause) {
        super(message, cause);
    }
}
