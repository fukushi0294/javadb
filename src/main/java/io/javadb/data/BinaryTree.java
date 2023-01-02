package io.javadb.data;

import java.util.Comparator;

public class BinaryTree<T> implements Tree<T> {
    Comparator<T> comparator;

    class Node {
        T value;
        Node left;
        Node right;
        Node parent;

        Node(T value) {
            this.value = value;
            right = null;
            left = null;
        }
    }

    public BinaryTree(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    protected Node root;

    @Override
    public void add(T value) {
        if (root == null) {
            root = new Node(value);
        } else {
            root = addRecursive(root, value);
        }
    }

    private Node addRecursive(Node current, T value) {
        if (comparator.compare(value, current.value) < 0) {
            if (current.left == null) {
                Node node = new Node(value);
                node.parent = current;
                return node;
            } else {
                current.left = addRecursive(current, value);
            }
        } else if (comparator.compare(value, current.value) > 0) {
            if (current.right == null) {
                Node node = new Node(value);
                node.parent = current;
                return node;
            } else {
                current.right = addRecursive(current, value);
            }
        } else {
            // value already exists
            return current;
        }
        return current;
    }

    @Override
    public boolean contains(T value) {
        return containsNodeRecursive(root, value);
    }

    private boolean containsNodeRecursive(Node current, T value) {
        if (current == null) {
            return false;
        }
        if (comparator.compare(value, current.value) == 0) {
            return true;
        }
        return comparator.compare(value, current.value) < 0
                ? containsNodeRecursive(current.left, value)
                : containsNodeRecursive(current.right, value);
    }

    @Override
    public void delete(T value) {
        root = deleteRecursive(root, value);
    }

    Node deleteRecursive(Node current, T value) {
        if (current == null) {
            return null;
        }

        if (comparator.compare(value, current.value) == 0) {
            // delete and re-balance
            Node smallest = pickSmallest(current);
            if (current.parent.left == current) {
                current.parent.left = smallest;
            }
            if (current.parent.right == current) {
                current.parent.right = smallest;
            }
            smallest.right = current.right;
            return current;
        }

        if (comparator.compare(value, current.value) < 0) {
            current.left = deleteRecursive(current.left, value);
        } else {
            current.right = deleteRecursive(current.right, value);
        }
        return current;
    }

    Node pickSmallest(Node current) {
        if (current.left == null) {
            Node parent = current.parent;
            parent.left = current.right;
            return current;
        }
        return pickSmallest(current.left);
    }

    public final T searchBiggest(Node current) {
        if (current.right == null) {
            return current.value;
        }
        return searchBiggest(current.right);
    }

    public final T searchSmallest(Node current) {
        if (current.left == null) {
            return current.value;
        }
        return searchBiggest(current.left);
    }
}
