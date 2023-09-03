package core.javadb.io.freespace;

import core.javadb.io.page.CtId;

import java.io.Serializable;
import java.util.Comparator;

public class FreeSpacePageEntry implements Serializable {
    private int size;
    private CtId ctId;

    public static final Comparator<FreeSpacePageEntry> comparator = Comparator.comparingInt(e -> e.size);

    public int getSize() {
        return size;
    }
}
