package com.jul.service;

import com.jul.model.Transfer;
import com.jul.utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;


public class TransferService {

    private final AccountService accountService;
    private final ArchiveService archiveService;
    private final LogService logService;

    private final String root;

    public TransferService(AccountService accountService, ArchiveService archiveService, LogService logService, String root) {
        this.accountService = accountService;
        this.archiveService = archiveService;
        this.logService = logService;
        this.root = root;
    }

    public void transfer() {
        File[] inputs = FileUtils.getAllTxtFiles(this.root);
        for (File file : inputs) {
            try {
                for (String line : Files.readAllLines(file.toPath())) {
                    if (line.isBlank()) {
                        continue;
                    }
                    Transfer transfer = null;
                    try {
                        transfer = new Transfer(line);
                        accountService.transfer(transfer);
                        logService.writeLog(file, transfer, null);
                    } catch (Exception e) {
                        if (transfer == null) {
                            logService.writeLog(file, line, e);
                        } else {
                            logService.writeLog(file, transfer, e);
                        }
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                System.out.println("Cannot open file " + file.getName() + " : " + e.getMessage());
                e.printStackTrace();
            } finally {
                try {
                    archiveService.archive(file);
                } catch (IOException e) {
                    System.out.println("Cannot archive file " + file.getName() + " : " + e.getClass().getSimpleName());
                    e.printStackTrace();
                }
            }
        }
    }

}
