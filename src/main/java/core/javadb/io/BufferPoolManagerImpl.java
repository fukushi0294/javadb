package core.javadb.io;

import core.javadb.io.page.Page;

import java.util.List;

public class BufferPoolManagerImpl implements BufferPoolManager {
    @Override
    public void append(Page page) {

    }

    @Override
    public Page fetchFreePage(String tableName) {
        return null;
    }

    @Override
    public List<Page> deFragment() {
        return null;
    }
}
