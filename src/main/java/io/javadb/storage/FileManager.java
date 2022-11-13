package io.javadb.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * create or fetch table file
 * there is no implementation to issue object id against database name or table name like Postgres
 */
public class FileManager {
    // TODO: read from config
    private static final String BASE_PATH = "/javadb/base";

    public void createDirectory(String databaseName) throws IOException {
        Path p = Paths.get(BASE_PATH, databaseName);
        Files.createDirectory(p);
    }

    public void createFile(String databaseName, String tableName) throws IOException {
        Path p = Paths.get(BASE_PATH, databaseName, tableName);
        Files.createFile(p);
    }

    public File getFile(Path p) throws FileNotFoundException {
        Path filename = p.getFileName();
        if (filename == null) {
            throw new FileNotFoundException();
        }
        return p.toFile();
    }
}
