package com.jul.model;

import com.jul.exception.ValidationException;

import java.math.BigDecimal;
import java.util.Objects;

public class Transfer {

    Account from;
    Account to;
    BigDecimal amount;

    public Transfer(String transfer) {
        String[] tokens = transfer.split(" ");

        if (tokens.length < 3) {
            throw new ValidationException("Incorrect transfer string: " + transfer);
        }

        this.from = new Account(tokens[0]);
        this.to = new Account(tokens[1]);
        this.amount = new BigDecimal(tokens[2]);

        if(amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValidationException("Transfer must be greater than 0");
        }
    }

    public Account getFrom() {
        return from;
    }

    public Account getTo() {
        return to;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transfer transfer = (Transfer) o;
        return Objects.equals(from, transfer.from) && Objects.equals(to, transfer.to) && Objects.equals(amount, transfer.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to, amount);
    }

    @Override
    public String toString() {
        return "Transfer{" +
                "from=" + from +
                ", to=" + to +
                ", amount=" + amount +
                '}';
    }
}
