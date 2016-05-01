package edu.frostburg.cosc610.huffmanencoder;

import java.util.Arrays;

/**
 * An implementation of the Priority Queue ADT<p>
 * This implementation uses an array-based Heap as the underlying data
 * structure. To avoid significant cost for adding many elements, specify the
 * estimated size when creating a new PriorityQueue.
 *
 * @author Kerwin Yoder
 * @version 2016.04.30
 * @param <E> The type of elements stored in the PriorityQueue
 */
public class PriorityQueue<E> {

    private Node<E>[] array;
    private int size;

    /**
     * Creates a new PriorityQueue with an initial size of 10
     */
    public PriorityQueue() {
        this(10);
    }

    /**
     * Creates a new PriorityQueue with the given initial size
     *
     * @param size the size of the PriorityQueue
     */
    public PriorityQueue(int size) {
        array = (Node<E>[]) new Node[size];
        this.size = 0;
    }

    /**
     * Gets the number of elements in the PriorityQueue
     *
     * @return the number of elements in the PriorityQueue
     */
    public int size() {
        return size;
    }

    /**
     * Inserts the given element into the priority using the given priority.
     *
     * @param element the element to insert
     * @param priority the priority of the element
     */
    public void insert(E element, int priority) {
        if (element == null) {
            throw new IllegalArgumentException("Cannot insert a null element");
        }

        if (size == array.length) {
            expand();
        }
        array[size] = new Node<>(element, priority);
        ++size;
        upheap(size - 1);
    }

    /**
     * Returns the element with the highest priority (lowest value)
     *
     * @return the element with the highest priority
     */
    public E remove() {
        if (size == 0) {
            return null;
        }
        E element = array[0].data;
        int last = size - 1;
        array[0] = array[last];
        array[last] = null;
        --size;
        downheap(0);
        return element;
    }

    /*
     * Restores the heap property from the given index up to the root
     * @param index the index from which the heap property will be restored up toward the root
     */
    private void upheap(int index) {
        if (index < 0 || (index >= size && size != 0)) {
            throw new IllegalArgumentException(String.format("%d is an invalid heap index. The heap size is %d.%n", index, size));
        }
        int priority = array[index].priority;
        int parent = parent(index);
        //parent == -1 implies index = root so this is not a problem
        while (index != 0 && priority < array[parent].priority) {
            swap(index, parent);
            index = parent;
            parent = parent(parent);
        }
    }

    /*
     * Restores the heap property from the given index down to the leaves
     * @param index the index from which the heap property will be restored down toward the root
     */
    private void downheap(int index) {
        if (index < 0 || (index >= size && size != 0)) {
            throw new IllegalArgumentException(String.format("%d is an invalid heap index. The heap size is %d.%n", index, size));
        }
        while (hasLeftChild(index)) {
            int leftIndex = leftChild(index);
            int smallIndex = leftIndex;
            if (hasRightChild(index)) {
                int rightIndex = rightChild(index);
                smallIndex = array[leftIndex].priority <= array[rightIndex].priority ? leftIndex : rightIndex;
            }
            if (array[index].priority > array[smallIndex].priority) {
                swap(index, smallIndex);
            }
            index = smallIndex;
        }
    }

    /*
     * Swaps the given elements in the queue
     * @param i the index of the first element
     * @param j the index of the second element
     */
    private void swap(int i, int j) {
        if (i >= size || j >= size) {
            throw new IllegalArgumentException("Invalid tree index.");
        }
        Node<E> temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    /*
     * Returns true if the node at the given index has a left child
     * @param index the index of the potential parent node
     * @return true if the node at the given index has a left child
     */
    private boolean hasLeftChild(int index) {
        return leftChild(index) != -1;
    }

    /*
     * Returns true if the node at the given index has a right child
     * @param index the index of the potential parent node
     * @return true if the node at the given index has a right child
     */
    private boolean hasRightChild(int index) {
        return rightChild(index) != -1;
    }

    /*
     * Returns the index of the given node's left child or -1 if the node has no left child
     * @param index the index of the parent node
     * @return the index of the given node's left child or -1 if the node has no left child
     */
    private int leftChild(int index) {
        if (index < 0 || (index >= size && size != 0)) {
            throw new IllegalArgumentException(String.format("%d is an invalid heap index. The heap size is %d.%n", index, size));
        }
        int child = 2 * index + 1;
        return child < size ? child : -1;
    }

    /*
     * Returns the index of the given node's right child or -1 if the node has no right child
     * @param index the index of the parent node
     * @return the index of the given node's right child or -1 if the node has no right child
     */
    private int rightChild(int index) {
        if (index < 0 || (index >= size && size != 0)) {
            throw new IllegalArgumentException(String.format("%d is an invalid heap index. The heap size is %d.%n", index, size));
        }
        int child = 2 * index + 2;
        return child < size ? child : -1;
    }

    /*
     * Returns the index of the given node's parent or -1 if the given node is the root
     * @param index the index of the node whose parent's index is to be returned
     * @return the index of the given node's parent or -1 if the given node is the root
     */
    private int parent(int index) {
        if (index < 0 || (index >= size && size != 0)) {
            throw new IllegalArgumentException(String.format("%d is an invalid heap index. The heap size is %d.%n", index, size));
        }
        if (index == 0) {
            return -1;
        }
        int parent = (index - 1) / 2;
        return parent < size ? parent : -1;
    }

    /*
     * Expands the capacity of the underlying array to twice its current capacity
     */
    private void expand() {
        array = Arrays.copyOf(array, array.length * 2);
    }

    /**
     * Represents a node in the Heap
     *
     * @param <E> The type of elements stored in the Node
     */
    static class Node<E> {

        private final E data;
        private int priority;

        /**
         * Creates a new Node with the given data and priority
         *
         * @param data the data to store in the node
         * @param priority the priority of the given element
         */
        public Node(E data, int priority) {
            this.data = data;
            this.priority = priority;
        }

        /**
         * Gets the data stored in the node
         *
         * @return the data stored in the node
         */
        public E getData() {
            return data;
        }

        /**
         * Gets the priority of the element stored in the node
         *
         * @return the priority of the element stored in the node
         */
        public int getPriority() {
            return priority;
        }

        /**
         * Sets the priority of the element stored in the node
         *
         * @param priority the new priority for the node
         */
        public void setPriority(int priority) {
            this.priority = priority;
        }
    }
}
