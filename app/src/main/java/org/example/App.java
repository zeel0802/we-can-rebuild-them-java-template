package org.example;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

class AVLTree {
    static class Node {
        int value, height;
        Node left, right;

        Node(int value) {
            this.value = value;
            this.height = 1;
        }
    }

    private Node root;

    private int height(Node node) {
        return node == null ? 0 : node.height;
    }

    private int getBalance(Node node) {
        return node == null ? 0 : height(node.left) - height(node.right);
    }

    private Node rotateRight(Node y) {
        Node x = y.left;
        Node T2 = x.right;
        x.right = y;
        y.left = T2;
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        return x;
    }

    private Node rotateLeft(Node x) {
        Node y = x.right;
        Node T2 = y.left;
        y.left = x;
        x.right = T2;
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        return y;
    }

    public void insert(int value) {
        root = insert(root, value);
    }

    private Node insert(Node node, int value) {
        if (node == null) return new Node(value);
        if (value < node.value) node.left = insert(node.left, value);
        else if (value > node.value) node.right = insert(node.right, value);
        else return node;

        node.height = Math.max(height(node.left), height(node.right)) + 1;
        int balance = getBalance(node);

        if (balance > 1 && value < node.left.value) return rotateRight(node);
        if (balance < -1 && value > node.right.value) return rotateLeft(node);
        if (balance > 1 && value > node.left.value) {
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }
        if (balance < -1 && value < node.right.value) {
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }
        return node;
    }

    public void delete(int value) {
        root = deleteNode(root, value);
    }

    private Node deleteNode(Node node, int value) {
        if (node == null) return node;
        if (value < node.value) node.left = deleteNode(node.left, value);
        else if (value > node.value) node.right = deleteNode(node.right, value);
        else {
            if (node.left == null || node.right == null) {
                Node temp = (node.left != null) ? node.left : node.right;
                if (temp == null) return null;
                else return temp;
            }
            Node temp = minValueNode(node.right);
            node.value = temp.value;
            node.right = deleteNode(node.right, temp.value);
        }

        node.height = Math.max(height(node.left), height(node.right)) + 1;
        int balance = getBalance(node);

        if (balance > 1 && getBalance(node.left) >= 0) return rotateRight(node);
        if (balance > 1 && getBalance(node.left) < 0) {
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }
        if (balance < -1 && getBalance(node.right) <= 0) return rotateLeft(node);
        if (balance < -1 && getBalance(node.right) > 0) {
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }
        return node;
    }

    private Node minValueNode(Node node) {
        while (node.left != null) node = node.left;
        return node;
    }

    public String serialize() {
        StringBuilder sb = new StringBuilder();
        serialize(root, sb);
        return sb.toString();
    }

    private void serialize(Node node, StringBuilder sb) {
        if (node == null) {
            sb.append("nil,");
            return;
        }
        sb.append(node.value).append(",");
        serialize(node.left, sb);
        serialize(node.right, sb);
    }

    public void deserialize(String data) {
        Queue<String> nodes = new LinkedList<>(Arrays.asList(data.split(",")));
        root = deserialize(nodes);
    }

    private Node deserialize(Queue<String> nodes) {
        if (nodes.isEmpty()) return null;
        String val = nodes.poll();
        if (val.equals("nil")) return null;
        Node node = new Node(Integer.parseInt(val));
        node.left = deserialize(nodes);
        node.right = deserialize(nodes);
        return node;
    }
}

public class App {
    public static void main(String[] args) {
        AVLTree tree = new AVLTree();
        tree.insert(3);
        tree.insert(4);
        tree.insert(5);
        tree.insert(6);
        System.out.println("Serialized AVL Tree: " + tree.serialize());
        tree.delete(6);
        System.out.println("Serialized AVL Tree after deletion: " + tree.serialize());
    }
}