package edu.frostburg.cosc610.huffmanencoder;

import java.util.HashMap;
import java.util.Map;

/**
 * An implementation of a Huffman Tree.<p>
 * This implementation provides only the basic functionality required for
 * Huffman Encoding.
 *
 * @author Kerwin Yoder
 * @version 2016.04.30
 * @param <E> the type of data stored in the HuffmanTree
 */
public class HuffmanTree<E> {

    private Node root;
    private Map<E, String> map;

    /**
     * Creates a new HuffmanTree with the given element and priority. The new
     * tree consists of a single node.
     *
     * @param element the element to store in the new Huffman Tree
     * @param priority the priority of the element
     */
    public HuffmanTree(E element, int priority) {
        root = new LeafNode(element, priority);
    }

    /**
     * Creates a new HuffmanTree by creating a new root node and attaching the
     * given trees as the left and right subtrees of the node.
     *
     * @param left the left subtree of the new tree
     * @param right the right subtree of the new tree
     */
    public HuffmanTree(HuffmanTree<E> left, HuffmanTree<E> right) {
        int priority = left.root.priority + right.root.priority;
        this.root = new InternalNode(priority, left.root, right.root);
    }

    /**
     * Gets a map containing the elements and their associated codes
     *
     * @return a map containing the elements as keys and their associated codes
     * as values
     */
    public Map<E, String> getMap() {
        if (map == null) {
            map = new HashMap<>();
            StringBuilder builder = new StringBuilder();
            initializeMap(root, builder);
        }
        return map;
    }

    /**
     * Returns the priority of the root node.
     *
     * @return the priority of the root node
     */
    public int getPriority() {
        return root.priority;
    }

    /*
     * Initializes the map by traversing the tree recursively
     * @param root the root of the tree or subtree
     * @param builder A StringBuilder used to generate the codes for each element
     */
    private void initializeMap(Node root, StringBuilder builder) {
        if (root instanceof LeafNode) {
            map.put((E) ((LeafNode) root).data, builder.toString());
        } else {
            builder.append(0);
            initializeMap(((InternalNode) root).left, builder);
            builder.setCharAt(builder.length() - 1, '1');
            initializeMap(((InternalNode) root).right, builder);
            builder.deleteCharAt(builder.length() - 1);
        }
    }

    /*
     * A node in the HuffmanTree
     */
    private static class Node {

        int priority;

        private Node(int priority) {
            this.priority = priority;
        }
    }

    /*
     * An internal node in the HuffmanTree
     */
    private static class InternalNode extends Node {

        Node left;
        Node right;

        private InternalNode(int priority, Node left, Node right) {
            super(priority);
            this.left = left;
            this.right = right;
        }
    }

    /*
     * A LeafNode in the HuffmanTree
     * @param <E> The type of the elements stored in the LeafNode
     */
    private static class LeafNode<E> extends Node {

        private final E data;

        private LeafNode(E data, int priority) {
            super(priority);
            this.data = data;
        }
    }
}
