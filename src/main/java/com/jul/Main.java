package com.jul;

import com.jul.service.AccountService;
import com.jul.service.ArchiveService;
import com.jul.service.LogService;
import com.jul.service.TransferService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Main {

    public static final String INPUT_ROOT = "./input";
    public static final String ARCHIVE_ROOT = "./archive";

    public static final String DB_ROOT = "./db/accounts.txt";

    public static final String LOG_FILE = "./output/log.txt";

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

    public static void main(String[] args) throws IOException {
        AccountService accountService = new AccountService(DB_ROOT);
        ArchiveService archiveService = new ArchiveService(ARCHIVE_ROOT);
        LogService logService = new LogService(formatter, LOG_FILE);
        TransferService service = new TransferService(accountService, archiveService, logService, INPUT_ROOT);

        Scanner scanner = new Scanner(System.in);

        System.out.println("Select an operation:");
        System.out.println("1: Process transfer files");
        System.out.println("2: Show transfer logs");
        System.out.println("3: Show transfer logs by period");
        System.out.println("0: Exit");

        try {
            String input;
            do {
                input = scanner.nextLine();

                switch (input) {
                    case "1": {
                        service.transfer();
                        System.out.println("Transfer successfully finished");
                        break;
                    }
                    case "2":
                        logService.findAll().forEach(logService::printLog);
                        break;
                    case "3":
                        LocalDateTime from = null, to = null;
                        try {
                            System.out.print("Enter start of period: ");
                            from = LocalDateTime.parse(scanner.nextLine(), formatter);

                            System.out.print("Enter end of period: ");
                            to = LocalDateTime.parse(scanner.nextLine(), formatter);
                        } catch (DateTimeParseException e) {
                            System.out.println("Invalid date time format: " + e.getMessage());
                        }

                        logService.findBy(from, to).forEach(logService::printLog);
                    default:
                        System.out.println("Incorrect operation!");
                }
            } while (!input.equals("0"));
        } finally {
            accountService.flush();
        }

    }
}