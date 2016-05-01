package huffmanencodertests;

import edu.frostburg.cosc610.huffmanencoder.PriorityQueue;

/**
 * A test class for the PriorityQueue
 *
 * @author Kerwin Yoder
 * @version 2016.04.30
 */
public class PriorityQueueTest {

    private static final PriorityQueue<Integer> queue = new PriorityQueue<>();

    public static void main(String[] args) {
        insertTest();
        getMinimumTest();
    }

    public static void insertTest() {
        for (int i = 0; i < 10; ++i) {
            queue.insert(i, i);
            assert (queue.size() == i + 1);
        }
    }

    public static void getMinimumTest() {
        int size = queue.size();
        for (int i = size; i > 0; --i) {
            System.out.println(queue.remove());
            assert (queue.size() == i - 1);
        }
    }
}
