package com.jul.model;

import com.jul.exception.ValidationException;

import java.util.Objects;

public class Account {

    public static final String pattern = "\\d{5}-\\d{5}";

    String number;

    public Account(String number) {
        if (number == null) {
            throw new ValidationException("account number should not be null");
        }
        if (number.length() != 11) {
            throw new ValidationException("account number should be exact 11 symbols");
        }
        if (!number.matches(pattern)) {
            throw new ValidationException("account number should match pattern XXXXX-XXXXX, where X - number");
        }
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(number, account.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }

    @Override
    public String toString() {
        return "Account{" +
                "number='" + number + '\'' +
                '}';
    }
}
