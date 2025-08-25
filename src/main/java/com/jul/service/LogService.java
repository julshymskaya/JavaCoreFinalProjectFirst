package com.jul.service;

import com.jul.model.LogRecord;
import com.jul.model.Transfer;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public interface LogService {

    void writeLog(File file, Transfer transfer, Exception e) throws IOException;

    void writeLog(File file, String line, Exception e) throws IOException;

    List<LogRecord> findAll() throws IOException;

    List<LogRecord> findBy(LocalDateTime from, LocalDateTime to) throws IOException;

}
