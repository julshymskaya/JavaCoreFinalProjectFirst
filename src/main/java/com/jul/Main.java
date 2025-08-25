package com.jul;

import com.jul.service.*;

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

        LogService logService;
        if(args.length > 0 && args[0].equals("--db")) {
            logService = new DbLogService();
            System.out.println("Log service implementation: database");
        } else {
            logService = new FileLogService(formatter, LOG_FILE);
            System.out.println("Log service implementation: file");
        }

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

                try {
                    switch (input) {
                        case "0" -> {
                        }
                        case "1" -> {
                            service.transfer();
                            System.out.println("Transfer successfully finished");
                        }
                        case "2" -> logService.findAll().forEach(x -> System.out.println(x.toMessage(formatter)));
                        case "3" -> {
                            LocalDateTime from, to;
                            try {
                                System.out.print("Enter start of period: ");
                                from = LocalDateTime.parse(scanner.nextLine(), formatter);

                                System.out.print("Enter end of period: ");
                                to = LocalDateTime.parse(scanner.nextLine(), formatter);

                                logService.findBy(from, to).forEach(x -> System.out.println(x.toMessage(formatter)));
                            } catch (DateTimeParseException e) {
                                System.out.println("Invalid date time format: " + e.getMessage());
                            }
                        }
                        default -> System.out.println("Incorrect operation!");
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            } while (!input.equals("0"));
        } finally {
            accountService.flush();
        }

    }
}