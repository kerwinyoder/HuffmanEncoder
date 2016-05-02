package huffmanencodertests;

import edu.frostburg.cosc610.huffmanencoder.HuffmanTree;
import java.util.Map;

/**
 * A test class for the HuffmanTree
 *
 * @author Kerwin Yoder
 * @version 2016.05.01
 */
public class HuffmanTreeTest {

    public static void main(String[] args) {
        HuffmanTree<Character> newTree;
        HuffmanTree<Character> tree = new HuffmanTree('a', (int) 'a');
        for (char c = 'b'; c <= 'z'; ++c) {
            newTree = new HuffmanTree<>(c, (int) c);
            tree = new HuffmanTree<>(tree, newTree);
        }
        Map map = tree.getMap();
        System.out.println(map.toString());
    }
}
