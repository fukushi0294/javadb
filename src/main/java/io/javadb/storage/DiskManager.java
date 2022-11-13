package io.javadb.storage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.Map;

public class DiskManager {
    private Catalog catalog;
    private FileManager fileManager;
    private PageDirectory pageDirectory;

    public void createTable() {

    }

    public Page createPage() throws IOException {
        String pageId = pageDirectory.getEmpty();
        if (pageId != null) {
            return fetch(pageId);
        }
        Page page = new Page();
        pageDirectory.append(page);
        return page;
    }

    /**
     * For index scan
     */
    public Page fetch(String pageId) throws IOException {
        Map.Entry<String, Integer> dataFileAndOffset = pageDirectory.getDataFileAndOffset(pageId);
        int offset = dataFileAndOffset.getValue();
        Path path = pageDirectory.getDatafilePath(pageId);
        File file = fileManager.getFile(path);
        Page fetched;
        try (FileInputStream fi = new FileInputStream(file)) {
            byte[] bytes = new byte[4 * 1024];
            fi.read(bytes, offset, 4 * 1024);
            fetched = Page.create(bytes);
            return fetched;
        }
    }

    /**
     * For page iterator
     */
    public Iterator<Page> fetchPageIterator(String tableName) {
        return null;
    }

}
