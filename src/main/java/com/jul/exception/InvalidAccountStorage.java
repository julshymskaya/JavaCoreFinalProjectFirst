package com.jul.exception;

public class InvalidAccountStorage extends RuntimeException {

    public InvalidAccountStorage(String message) {
        super(message);
    }

}
