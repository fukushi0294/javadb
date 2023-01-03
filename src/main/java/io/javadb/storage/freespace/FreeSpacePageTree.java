package io.javadb.storage.freespace;

import io.javadb.data.BalancedBinaryTree;

import java.util.LinkedList;
import java.util.List;

public class FreeSpacePageTree extends BalancedBinaryTree<FreeSpacePageEntry> {
    private int max;
    private int capacity;

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

    List<FreeSpacePageEntry> valueList() {
        LinkedList<FreeSpacePageEntry> valueList = new LinkedList<>();
        Node node = this.pickSmallest(this.root);
        while (node != null) {
            valueList.add(node.getValue());
            node = this.pickSmallest(this.root);
        }
        return valueList;
    }
}
