package io.javadb.storage;

import io.javadb.storage.page.Page;

import java.io.IOException;
import java.util.Map;

public class HeapFile {
    private final String databaseName;

    public HeapFile(String databaseName) {
        this.databaseName = databaseName;
    }

    public Page createPage(String tableName) throws IOException {
        Page page = new Page(databaseName, tableName);
        append(page);
        return page;
    }

    public Page loadPage(String tableName) throws IOException {
        throw new RuntimeException();
    }

    public void append(Page page) {
    }

    // for index scan
    public Map.Entry<String, Integer> getDataFileAndOffset(String pageId) {
        return null;
    }

    // periodically write to persistent disk
    public void persist() {

    }
}
