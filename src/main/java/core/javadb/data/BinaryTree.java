package core.javadb.data;

import java.util.Comparator;

public class BinaryTree<T> implements Tree<T> {
    protected Comparator<T> comparator;

    protected class Node {
        T value;
        Node left;
        Node right;

        public Node(T value) {
            this.value = value;
            right = null;
            left = null;
        }

        public T getValue() {
            return value;
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
            addRecursive(root, value);
        }
    }

    /**
     * @param current
     * @param value
     * @return edge node which is just appended
     */
    protected Node addRecursive(Node current, T value) {
        if (comparator.compare(value, current.value) < 0) {
            if (current.left == null) {
                Node node = new Node(value);
                current.left = node;
                return node;
            } else {
                return addRecursive(current.left, value);
            }
        } else if (comparator.compare(value, current.value) > 0) {
            if (current.right == null) {
                Node node = new Node(value);
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
            if (current.left == null && current.right == null) {
                return null;
            }
            if (current.right == null) {
                return current.left;
            }

            if (current.left == null) {
                return current.right;
            }

            Node smallest = searchSmallest(current.right);
            current.value = smallest.value;
            current.right = deleteRecursive(current.right, smallest.value);
            return current;
        }

        if (comparator.compare(value, current.value) < 0) {
            current.left = deleteRecursive(current.left, value);
        } else {
            current.right = deleteRecursive(current.right, value);
        }
        return current;
    }

    protected Node pickSmallest(Node current) {
        Node smallest = searchSmallest(current.left);
        deleteRecursive(current.left, smallest.value);
        return smallest;
    }

    public final T searchBiggest(Node current) {
        if (current.right == null) {
            return current.value;
        }
        return searchBiggest(current.right);
    }

    public final Node searchSmallest(Node current) {
        if (current.left == null) {
            return current;
        }
        return searchSmallest(current.left);
    }
}
