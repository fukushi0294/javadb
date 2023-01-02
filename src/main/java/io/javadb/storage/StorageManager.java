package io.javadb.storage;

import io.javadb.storage.page.CtId;
import io.javadb.storage.page.Page;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StorageManager {
    private final Map<String, FileManager> databaseNameToFile = new HashMap<>();

    public void createDatabase(String databaseName) throws IOException {
        FileManager fm = new FileManager(databaseName);
        databaseNameToFile.put(databaseName, fm);
    }

    public void createTable(String databaseName, String tableName) throws IOException {
        FileManager fm = databaseNameToFile.get(databaseName);
        fm.createFile(tableName);
    }

    /**
     * For index scan
     */
    public Page fetch(String databaseName, CtId ctId) throws IOException {
        int offset = ctId.offset;
        Path path = Path.of(ctId.pageId);
        Page fetched;
        FileManager fileManager = databaseNameToFile.get(databaseName);
        try (FileInputStream fi = fileManager.getFile(path)) {
            byte[] bytes = new byte[4 * 1024];
            fi.read(bytes, offset, 4 * 1024);
            fetched = Page.create(bytes);
            return fetched;
        }
    }

    public void persist(String databaseName, List<Page> pages) throws IOException {
        FileManager fileManager = databaseNameToFile.get(databaseName);
        Path path = Path.of(pages.get(0).ctId.pageId);
        try (FileInputStream fi = fileManager.getFile(path)) {
            DataFile dataFile = new DataFile(fi);
            dataFile.append(pages);
        }
    }

}
