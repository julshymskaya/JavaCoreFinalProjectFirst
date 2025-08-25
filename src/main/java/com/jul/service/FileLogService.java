package com.jul.service;

import com.jul.model.LogRecord;
import com.jul.model.Transfer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class FileLogService implements LogService {

    private final String root;

    private final DateTimeFormatter formatter;

    public FileLogService(DateTimeFormatter formatter, String root) throws IOException {
        this.root = root;
        this.formatter = formatter;
        if (Files.notExists(Path.of(root))) {
            Files.createFile(Path.of(root));
        }
    }

    public void writeLog(File file, Transfer transfer, Exception e) throws IOException {
        LogRecord record = LogRecord.create(file, transfer, e);
        Files.writeString(Path.of(root), record.toMessage(formatter) + "\n", StandardOpenOption.APPEND);
    }

    public void writeLog(File file, String line, Exception e) throws IOException {
        String[] tokens = line.split(" ");
        LogRecord record;
        if (tokens.length < 3) {
            record = LogRecord.create(file, line, e);
        } else {
            record = LogRecord.create(file, tokens[0], tokens[1], tokens[2], e);
        }
        Files.writeString(Path.of(root), record.toMessage(formatter) + "\n", StandardOpenOption.APPEND);
    }

    public List<LogRecord> findAll() throws IOException {
        return Files.readAllLines(Path.of(root)).stream().map(x -> LogRecord.parse(x, formatter)).toList();
    }

    public List<LogRecord> findBy(LocalDateTime from, LocalDateTime to) throws IOException {
        return findAll().stream().filter(x -> x.getDateTime().isAfter(from) && x.getDateTime().isBefore(to)).toList();
    }


}
