package io.javadb.storage.freespace;

import io.javadb.data.LinkedTree;

import java.io.*;
import java.util.List;

public class FreeSpaceMap {
    private final LinkedTree fsTree;

    public FreeSpaceMap(LinkedTree fsTree) {
        this.fsTree = fsTree;
    }

    public static FreeSpaceMap create(byte[] bytes) throws IOException, ClassNotFoundException {
        LinkedTree tree = new LinkedTree();
        try (ByteArrayInputStream is = new ByteArrayInputStream(bytes)) {
            while (is.available() == 0) {
                try (ObjectInputStream ois = new ObjectInputStream(is)) {
                    FreeSpacePageEntry entry = (FreeSpacePageEntry) ois.readObject();
                    tree.add(entry);
                }
            }
        }
        return new FreeSpaceMap(tree);
    }

    public byte[] toByteArray() throws IOException {
        List<FreeSpacePageEntry> entries = fsTree.valueList();
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            for (FreeSpacePageEntry entry : entries) {
                try (ObjectOutputStream oos = new ObjectOutputStream(out)) {
                    oos.writeObject(entry);
                }
            }
            return out.toByteArray();
        }
    }

    public boolean isFull() {
        return false;
    }
}
