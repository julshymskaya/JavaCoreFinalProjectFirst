package com.jul.service;

import com.jul.exception.AccountNotFoundException;
import com.jul.exception.InvalidAccountStorage;
import com.jul.exception.NotEnoughFundsException;
import com.jul.model.Account;
import com.jul.model.Transfer;

import java.io.*;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class AccountService {

    private final String fileName;
    private final Map<Account, BigDecimal> accounts;

    public AccountService(String fileName) throws IOException {
        this.fileName = fileName;
        List<String> lines = Files.readAllLines(new File(fileName).toPath());
        accounts = new LinkedHashMap<>(lines.size());
        lines
                .stream()
                .filter(x -> !x.isEmpty())
                .forEach(line -> {
                    String[] tokens = line.split(" ");
                    if (tokens.length != 2) {
                        throw new InvalidAccountStorage("Incorrect account data: " + line);
                    }
                    Account account = new Account(tokens[0]);
                    BigDecimal amount = new BigDecimal(tokens[1]);
                    accounts.put(account, amount);
                });
    }

    public void transfer(Transfer transfer) {
        BigDecimal amountFrom = getBalance(transfer.getFrom());
        BigDecimal amountTo = getBalance(transfer.getTo());

        if (amountFrom.compareTo(transfer.getAmount()) < 0) {
            throw new NotEnoughFundsException();
        }

        accounts.replace(transfer.getFrom(), amountFrom.subtract(transfer.getAmount()));
        accounts.replace(transfer.getTo(), amountTo.add(transfer.getAmount()));
    }

    public BigDecimal getBalance(Account account) {
        BigDecimal balance = accounts.get(account);

        if (balance == null) {
            throw new AccountNotFoundException(account);
        }
        return balance;
    }

    public void flush() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), StandardCharsets.UTF_8))) {
            accounts.forEach((account, balance) -> {
                try {
                    writer.write(account.getNumber() + " " + balance);
                    writer.newLine();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

}
