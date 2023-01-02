package io.javadb.storage.freespace;

import io.javadb.storage.page.CtId;

import java.util.LinkedList;
import java.util.List;

public class FreeSpace {
    static class FreeSpaceMapTree {
        private static final int leafSize = 1024;
        private int depth;
        private Node root;

        public FreeSpaceMapTree() {
            this.depth = 0;
            this.root = new Node();
        }

        static class Node {
            LinkedList<Node> children = new LinkedList<>();
            List<CtId> fsmList = new LinkedList<>();

            boolean isEdge() {
                return children.isEmpty();
            }

            void add(CtId ctId) {
                fsmList.add(ctId);
            }
        }

        Node getEdge(Node current) {
            if (current.isEdge()) {
                return current;
            }
            return current.children.getLast();
        }

        public void addFsm(CtId ctId) {
            Node edge = getEdge(root);
            if (edge.fsmList.size() == leafSize) {
                this.extend();
                addFsm(ctId);
            } else {
                edge.add(ctId);
            }
        }

        public CtId search(int size) {
            // TODO:
            return null;
        }

        public void extend() {
            Node currentRoot = root;
            root = new Node();
            root.children.add(currentRoot);
            depth++;
        }

    }

    public void update(CtId ctId) {

    }

    public CtId search(int size) {
        return null;
    }

    public void vacuum() {

    }

}
