package io.javadb.storage.freespace;

import io.javadb.storage.page.CtId;

public class FreeSpace {
    private FreeSpacePageTree root;

    static class TreeNode {
        public FreeSpacePageTree current;
        public FreeSpacePageTree next;
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
