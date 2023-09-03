package core.javadb.io;

import core.javadb.io.page.Page;
import core.javadb.io.page.Tuple;

import java.util.List;
import java.util.function.Predicate;

public class HeapFile implements PageIO {
    private final String databaseName;
    private final String tableName;
    private final StorageManager storageManager;

    public HeapFile(String databaseName,
                    String tableName,
                    StorageManager storageManager) {
        this.databaseName = databaseName;
        this.tableName = tableName;
        this.storageManager = storageManager;
    }

    @Override
    public Page insert(Tuple tuple) {
        Page page = this.storageManager.fetchNew(databaseName, tableName);
        page.insert(tuple);
        return page;
    }

    @Override
    public List<Tuple> search(Predicate<Tuple> condition) {
        return null;
    }

    @Override
    public Page delete(Tuple tuple, Predicate<Tuple> condition) {
        return null;
    }

    @Override
    public Page update(Tuple tuple, Predicate<Tuple> condition) {
        return null;
    }
}
