package com.jul.utils;

import java.io.File;

public class FileUtils {

    public static File[] getAllTxtFiles(String folder) {
        File dir = new File(folder);
        return dir.listFiles((dir1, name) -> name.endsWith(".txt"));
    }

}
