package io.javadb.data;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class LinkedTree<T> extends BalancedBinaryTree<T> {
    private T max;
    private final int capacity;

    public LinkedTree(Comparator<T> comparator, int capacity) {
        super(comparator);
        this.capacity = capacity;
    }

    class NextPtr extends Node {
        LinkedTree<T> next;
        public NextPtr(T value) {
            super(value);
            next = new LinkedTree<>(comparator, capacity);
        }
    }


    @Override
    public void add(T value) {
        if (root == null) {
            root = new Node(value);
        } else {
            Node node = addRecursive(root, value);
            if (node instanceof LinkedTree.NextPtr) {
                ((NextPtr) node).next.add(value);
            } else {
                root = reBalance(root);
            }
        }

        if (comparator.compare(max, value) > 0) {
            max = value;
        }
    }

    @Override
    protected Node addRecursive(Node current, T value) {
        if (current instanceof LinkedTree.NextPtr) {
            return current;
        }

        if (comparator.compare(value, current.value) < 0) {
            if (current.left == null) {
                int depth = height(root);
                Node node;
                if (depth == capacity) {
                    node = new NextPtr(value);
                } else {
                    node = new Node(value);
                }
                current.left = node;
                return node;
            } else {
                return addRecursive(current.left, value);
            }
        } else if (comparator.compare(value, current.value) > 0) {
            if (current.right == null) {
                int depth = height(root);
                Node node;
                if (depth == capacity) {
                    node = new NextPtr(value);
                } else {
                    node = new Node(value);
                }
                current.right = node;
                return node;
            } else {
                return addRecursive(current.right, value);
            }
        } else {
            // value already exists
            return current;
        }
    }

    @Override
    public void delete(T entry) {
        super.delete(entry);
        if (comparator.compare(max, entry) == 0) {
            max = searchBiggest(root);
        }
    }

    public List<T> valueList() {
        LinkedList<T> valueList = new LinkedList<>();
        Node node = this.pickSmallest(this.root);
        while (node != null) {
            valueList.add(node.value);
            node = this.pickSmallest(this.root);
        }
        return valueList;
    }
}
