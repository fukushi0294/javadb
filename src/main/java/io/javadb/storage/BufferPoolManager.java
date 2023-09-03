package io.javadb.storage;

import io.javadb.storage.page.Page;

import java.util.List;

public interface BufferPoolManager {
    void append(Page page);
    Page fetchFreePage(String tableName);
    List<Page> deFragment();
}
