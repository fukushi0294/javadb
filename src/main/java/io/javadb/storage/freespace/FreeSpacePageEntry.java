package io.javadb.storage.freespace;

import io.javadb.storage.page.CtId;

import java.util.Comparator;

public class FreeSpacePageEntry {
    private int size;
    private CtId ctId;

    public static final Comparator<FreeSpacePageEntry> comparator = Comparator.comparingInt(e -> e.size);

    public int getSize() {
        return size;
    }
}
