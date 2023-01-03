package io.javadb.storage.freespace;

import io.javadb.data.LinkedTree;
import io.javadb.storage.page.CtId;

public class FreeSpace {
    private LinkedTree root;

    static class TreeNode {
        public LinkedTree current;
        public LinkedTree next;
    }

    public void update(CtId ctId) {

    }

    public CtId search(int size) {
        return null;
    }

    public void vacuum() {

    }

    public void extend() {

    }

}
