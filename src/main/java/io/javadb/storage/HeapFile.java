package io.javadb.storage;

import java.io.IOException;
import java.util.Map;

public class HeapFile implements Serializable {
    private BufferPoolManager bufferPoolManager;
    private StorageManager storageManager;

    public Page createPage(String databaseName) throws IOException {
        Page page = new Page();
        append(page);
        return page;
    }

    public void append(Page page) {
        // append page to buffer pool
    }

    // for index scan
    public Map.Entry<String, Integer> getDataFileAndOffset(String pageId) {
        return null;
    }

    // periodically write to persistent disk
    public void persist() {

    }
}
