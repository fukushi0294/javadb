package io.javadb.storage;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Queue;

/**
 * create or fetch table file
 * there is no implementation to issue object id against database name or table name like Postgres
 */
public class FileManager {
    // TODO: read from config
    private static final String BASE_PATH = "/javadb/base";
    private Queue fdPool;

    public void createDirectory(String databaseName) throws IOException {
        Path p = Paths.get(BASE_PATH, databaseName);
        Files.createDirectory(p);
    }

    public void createFile(String databaseName, String tableName) throws IOException {
        Path p = Paths.get(BASE_PATH, databaseName, tableName);
        FileInputStream fi = new FileInputStream(Files.createFile(p).toFile());
         FileDescriptor fd = fi.getFD();
         fd.valid();
    }

    public File getFile(Path p) throws FileNotFoundException {
        Path filename = p.getFileName();
        if (filename == null) {
            throw new FileNotFoundException();
        }
        return p.toFile();
    }
}
