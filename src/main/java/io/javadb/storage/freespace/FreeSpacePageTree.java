package io.javadb.storage.freespace;

import io.javadb.data.BinaryTree;
import io.javadb.storage.freespace.FreeSpacePageEntry;


public class FreeSpacePageTree extends BinaryTree<FreeSpacePageEntry> {
    private int max;

    public FreeSpacePageTree() {
        super(FreeSpacePageEntry.comparator);
        this.max = 0;
    }

    @Override
    public void add(FreeSpacePageEntry entry) {
        super.add(entry);
        max = Math.max(max, entry.getSize());
    }

    @Override
    public void delete(FreeSpacePageEntry entry) {
        super.delete(entry);
        if (max == entry.getSize()) {
            FreeSpacePageEntry maxEntry = searchBiggest(super.root);
            max = maxEntry.getSize();
        }
    }

}
