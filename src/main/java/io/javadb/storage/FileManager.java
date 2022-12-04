package io.javadb.storage;

import io.javadb.data.LRUCache;

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

/**
 * create or fetch table file
 * there is no implementation to issue object id against database name or table name like Postgres
 */
public class FileManager {
    private static final String BASE_PATH = "/javadb/base";
    private final LRUCache<String, FileDescriptor> fdPool;
    private final String databaseName;

    public FileManager(String databaseName) throws IOException {
        this.databaseName = databaseName;
        this.fdPool = new LRUCache<>(256);
        Path p = Paths.get(BASE_PATH, databaseName);
        if (!Files.exists(p)) {
            Files.createDirectory(p);
        }
    }

    public synchronized FileInputStream createFile(String tableName) throws IOException {
        Path p = Paths.get(BASE_PATH, databaseName, tableName);
        FileInputStream fi = new FileInputStream(Files.createFile(p).toFile());
        FileDescriptor fd = fi.getFD();
        fdPool.put(tableName, fd);
        return fi;
    }

    public FileInputStream getFile(Path p) throws IOException {
        Path filename = p.getFileName();
        if (filename == null) {
            throw new FileNotFoundException();
        }
        Optional<FileDescriptor> optionalFd = fdPool.get(p.toString());
        if (optionalFd.isPresent()) {
            FileDescriptor fd = optionalFd.get();
            if (fd.valid()) {
                return new FileInputStream(fd);
            }
        }
        FileInputStream fi = new FileInputStream(p.toFile());
        FileDescriptor newFd = fi.getFD();
        fdPool.put(p.toString(), newFd);
        return fi;
    }
}
