package io.javadb.data;

import java.util.Comparator;

public class BalancedBinaryTree<T> extends BinaryTree<T> {
    public BalancedBinaryTree(Comparator<T> comparator) {
        super(comparator);
    }
    @Override
    public void add(T value) {
        super.add(value);
        root = reBalance(root);
    }

    int height(Node current) {
        if (current == null) {
            return 0;
        }
        return 1 + Math.max(height(current.left), height(current.right));
    }

    boolean isBalanced(Node current) {
        if (current == null) {
            return true;
        }

        int lh = height(current.left);
        int rh = height(current.right);

        return (Math.abs(lh - rh) <= 1 && isBalanced(current.left) && isBalanced(current.right));
    }

    Node reBalance(Node current) {
        if (isBalanced(current)) {
            return current;
        }

        if (current.right == null) {
            addRecursive(current.left, current.value);
            return current.left;
        }
        if (current.left == null) {
            addRecursive(current.right, current.value);
            return current.right;
        }

        current.right = reBalance(current.right);
        current.left = reBalance(current.left);

        return current;
    }
}
