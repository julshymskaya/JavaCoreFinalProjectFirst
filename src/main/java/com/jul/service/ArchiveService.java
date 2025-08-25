package com.jul.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;


public class ArchiveService {

    private final String root;

    public ArchiveService(String root) {
        this.root = root;
    }

    public void archive(File file) throws IOException {
        try {
            Files.copy(file.toPath(), new File(root).toPath().resolve(file.getName()));
        } finally {
            Files.delete(file.toPath());
        }
    }

}
