package io.javadb.storage.freespace;

import io.javadb.data.LinkedTree;
import io.javadb.data.NextPtr;

public class FSPagePtr implements NextPtr<FreeSpacePageEntry> {
    @Override
    public FreeSpacePageTree getNext() {
        return null;
    }
}
