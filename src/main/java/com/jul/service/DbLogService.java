package com.jul.service;

import com.jul.model.LogRecord;
import com.jul.model.Transfer;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DbLogService implements LogService {

    @Override
    public void writeLog(File file, Transfer transfer, Exception e) throws IOException {
        writeLog(LogRecord.create(file, transfer, e));
    }

    @Override
    public void writeLog(File file, String line, Exception e) throws IOException {
        writeLog(LogRecord.create(file, line, e));
    }

    @Override
    public List<LogRecord> findAll() throws IOException {
        try(Connection connection = createConnection()) {
            String sql = "SELECT date_time, file_name, message, status FROM record_log";

            PreparedStatement statement = connection.prepareStatement(sql);

            return executeAndConvert(statement);
        } catch (Exception e) {
            throw new IOException(e);
        }
    }

    @Override
    public List<LogRecord> findBy(LocalDateTime from, LocalDateTime to) throws IOException {
        try(Connection connection = createConnection()) {
            String sql = "SELECT date_time, file_name, message, status FROM record_log " +
                    "WHERE date_time BETWEEN ? and ?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setTimestamp(1, Timestamp.valueOf(from));
            statement.setTimestamp(2, Timestamp.valueOf(to));

           return executeAndConvert(statement);
        } catch (Exception e) {
            throw new IOException(e);
        }
    }

    void writeLog(LogRecord record) throws IOException {
        try (Connection connection = createConnection()) {
            String sql = "INSERT INTO record_log (date_time, file_name, message, status) VALUES (?, ?, ?, ?)";
            connection.setAutoCommit(true);

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setObject(1, record.getDateTime());
            statement.setString(2, record.getFileName());
            statement.setString(3, record.getMessage());
            statement.setString(4, record.getStatus());

            statement.executeUpdate();
        } catch (Exception exception) {
            throw new IOException(exception.getMessage());
        }
    }

    Connection createConnection() throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/postgres";
        return DriverManager.getConnection(url,"postgres","qwerty");
    }

    List<LogRecord> executeAndConvert(PreparedStatement statement) throws SQLException {
        ResultSet result = statement.executeQuery();

        List<LogRecord> list = new ArrayList<>();
        while (result.next()) {
            LocalDateTime dateTime = result.getTimestamp(1).toLocalDateTime();
            String fileName = result.getString(2);
            String message = result.getString(3);
            String status = result.getString(4);

            list.add(new LogRecord(dateTime, fileName, message, status));
        }
        return list;
    }

}
