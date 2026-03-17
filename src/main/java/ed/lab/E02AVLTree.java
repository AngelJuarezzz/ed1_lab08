package ed.lab;

import java.util.Comparator;

public class E02AVLTree<T> {

    private final Comparator<T> comparator;
    private Node<T> root;
    private int size;

    public E02AVLTree(Comparator<T> comparator) {
        this.comparator = comparator;
        this.root = null;
        this.size = 0;
    }

    public void insert(T value) {
        root = insert(root, value);
    }

    private Node<T> insert(Node<T> node, T value) {
        if (node == null) {
            size++;
            return new Node<>(value);
        }

        int cmp = comparator.compare(value, node.value);

        if (cmp < 0) {
            node.left = insert(node.left, value);
        } else if (cmp > 0) {
            node.right = insert(node.right, value);
        } else {
            return node;
        }

        updateHeight(node);
        return balance(node);
    }

    public void delete(T value) {
        root = delete(root, value);
    }

    private Node<T> delete(Node<T> node, T value) {
        if (node == null) return null;

        int cmp = comparator.compare(value, node.value);

        if (cmp < 0) {
            node.left = delete(node.left, value);
        } else if (cmp > 0) {
            node.right = delete(node.right, value);
        } else {
            if (node.left == null || node.right == null) {
                size--;
                node = (node.left != null) ? node.left : node.right;
            } else {
                Node<T> min = getMin(node.right);
                node.value = min.value;
                node.right = delete(node.right, min.value);
            }
        }

        if (node == null) return null;

        updateHeight(node);
        return balance(node);
    }

    public T search(T value) {
        Node<T> n = root;

        while (n != null) {
            int cmp = comparator.compare(value, n.value);
            if (cmp == 0) return n.value;
            if (cmp < 0) n = n.left;
            else n = n.right;
        }

        return null;
    }

    public int height() {
        return root == null ? 0 : root.height;
    }

    public int size() {
        return size;
    }

    private Node<T> balance(Node<T> node) {
        int bf = getBalance(node);

        if (bf > 1) {
            if (getBalance(node.left) < 0) {
                node.left = rotateLeft(node.left);
            }
            return rotateRight(node);
        }

        if (bf < -1) {
            if (getBalance(node.right) > 0) {
                node.right = rotateRight(node.right);
            }
            return rotateLeft(node);
        }

        return node;
    }

    private int getBalance(Node<T> node) {
        return node == null ? 0 : getHeight(node.left) - getHeight(node.right);
    }

    private int getHeight(Node<T> node) {
        return node == null ? 0 : node.height;
    }

    private void updateHeight(Node<T> node) {
        node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));
    }

    private Node<T> rotateLeft(Node<T> x) {
        Node<T> y = x.right;
        Node<T> t = y.left;

        y.left = x;
        x.right = t;

        updateHeight(x);
        updateHeight(y);

        return y;
    }

    private Node<T> rotateRight(Node<T> y) {
        Node<T> x = y.left;
        Node<T> t = x.right;

        x.right = y;
        y.left = t;

        updateHeight(y);
        updateHeight(x);

        return x;
    }

    private Node<T> getMin(Node<T> node) {
        while (node.left != null) node = node.left;
        return node;
    }

    private static class Node<T> {
        T value;
        Node<T> left;
        Node<T> right;
        int height;

        Node(T value) {
            this.value = value;
            this.height = 1;
        }
    }
}