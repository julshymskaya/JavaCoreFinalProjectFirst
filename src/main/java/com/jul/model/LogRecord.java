package com.jul.model;

import com.jul.exception.ValidationException;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

public class LogRecord {

    private final LocalDateTime dateTime;
    private final String fileName;
    private final String message;
    private final String status;

    public static LogRecord create(File file, Transfer transfer, Exception e) {
        return create(file, transfer.getFrom().getNumber(), transfer.getTo().getNumber(), transfer.getAmount().toString(), e);
    }

    public static LogRecord create(File file, String from, String to, String amount, Exception e) {
        return create(file, "перевод с " + from + " на " + to + " " + amount, e);
    }

    public static LogRecord create(File file, String message, Exception e) {
        return new LogRecord(LocalDateTime.now(), file.getName(), message, e == null ? "успешно обработан" : "ошибка во время обработки, " + e.getMessage());
    }

    public static LogRecord parse(String rec, DateTimeFormatter formatter) {
        String[] tokens = rec.split(" \\| ");
        if (tokens.length != 4) {
            throw new ValidationException("Incorrect log record " + rec);
        }
        return new LogRecord(LocalDateTime.parse(tokens[0], formatter), tokens[1], tokens[2], tokens[3]);
    }

    public LogRecord(LocalDateTime dateTime, String fileName, String message, String status) {
        this.dateTime = dateTime;
        this.fileName = fileName;
        this.message = message;
        this.status = status;
    }

    public String toMessage(DateTimeFormatter formatter) {
        return String.join(" | ", List.of(dateTime.format(formatter), fileName, message, status));
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getFileName() {
        return fileName;
    }

    public String getMessage() {
        return message;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LogRecord log = (LogRecord) o;
        return Objects.equals(dateTime, log.dateTime) && Objects.equals(fileName, log.fileName) && Objects.equals(message, log.message) && Objects.equals(status, log.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateTime, fileName, message, status);
    }

    @Override
    public String toString() {
        return "Log{" +
                "dateTime=" + dateTime +
                ", fileName='" + fileName + '\'' +
                ", message='" + message + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
