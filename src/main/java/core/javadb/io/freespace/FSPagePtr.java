package core.javadb.io.freespace;

import core.javadb.data.NextPtr;

public class FSPagePtr implements NextPtr<FreeSpacePageEntry> {
    @Override
    public FreeSpacePageTree getNext() {
        return null;
    }
}
