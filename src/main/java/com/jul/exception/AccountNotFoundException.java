package com.jul.exception;

import com.jul.model.Account;

public class AccountNotFoundException extends RuntimeException {

    public AccountNotFoundException(Account account) {
        super("Account " + account.getNumber() + " not found");
    }

}
