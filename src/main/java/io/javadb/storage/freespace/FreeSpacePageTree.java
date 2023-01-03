package io.javadb.storage.freespace;

import io.javadb.data.LinkedTree;
import io.javadb.data.NextPtr;
import org.checkerframework.checker.units.qual.C;

import java.io.*;
import java.util.Comparator;
import java.util.List;

public class FreeSpacePageTree extends LinkedTree<FreeSpacePageEntry> {
    public FreeSpacePageTree(Comparator<FreeSpacePageEntry> comparator, int capacity) {
        super(comparator, capacity);
    }

    @Override
    protected NextPtr<FreeSpacePageEntry> nextPtr() {
        return null;
    }

    public static FreeSpacePageTree create(byte[] bytes) throws IOException, ClassNotFoundException {
        FreeSpacePageTree fsTree = new FreeSpacePageTree(Comparator.comparingInt(FreeSpacePageEntry::getSize), 1024);
        try (ByteArrayInputStream is = new ByteArrayInputStream(bytes)) {
            while (is.available() == 0) {
                try (ObjectInputStream ois = new ObjectInputStream(is)) {
                    FreeSpacePageEntry entry = (FreeSpacePageEntry) ois.readObject();
                    fsTree.add(entry);
                }
            }
        }
        return fsTree;
    }

    public byte[] toByteArray() throws IOException {
        List<FreeSpacePageEntry> entries = this.valueList();
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            for (FreeSpacePageEntry entry : entries) {
                try (ObjectOutputStream oos = new ObjectOutputStream(out)) {
                    oos.writeObject(entry);
                }
            }
            return out.toByteArray();
        }
    }
}
