package com.jul.exception;


public class NotEnoughFundsException extends RuntimeException {

    public NotEnoughFundsException() {
        super("Account has not enough funds on balance");
    }

}
