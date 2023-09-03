package core.javadb.io;

import core.javadb.io.page.Page;

import java.util.List;

public interface BufferPoolManager {
    void append(Page page);
    Page fetchFreePage(String tableName);
    List<Page> deFragment();
}
