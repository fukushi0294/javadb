package core.javadb.io;

import core.javadb.io.page.Page;
import core.javadb.io.page.Tuple;

import java.io.IOException;
import java.util.List;
import java.util.function.Predicate;

public class StorageManager {
    private BufferPoolManager bufferPoolManager;
    private FileManager fileManager;

    public void createDatabase(String databaseName) throws IOException {
        fileManager = new FileManager(databaseName);
    }

    public void createTable(String tableName) throws IOException {
        if (fileManager == null) {
            throw new IllegalStateException();
        }
        fileManager.createFile(tableName);
    }

    // new page load to memory
    public Page fetchNew(String databaseName, String tableName) {
        Page page = this.bufferPoolManager.fetchFreePage(tableName);
        if (page != null) {
            return page;
        }
        page = new Page(databaseName, tableName);
        this.bufferPoolManager.append(page);
        return page;
    }

    public List<Tuple> scan(Predicate<Tuple> condition) {
        // TODO
        return null;
    }

    public void persist() throws IOException {
        List<Page> pages = bufferPoolManager.deFragment();
        fileManager.persist(pages);
    }

}
