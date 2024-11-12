package com.stater.intk.model.exception;

public class BusinessException extends RuntimeException {
    private static final long serialVersionUID = 874387341803198776L;

    public BusinessException(String message) {
        super(message);
    }
}
