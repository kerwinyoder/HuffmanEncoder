package edu.frostburg.cosc610.huffmanencoder;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

/**
 * A Huffman Encoder for ASCII Characters (including the extended ASCII codes)
 *
 * @author Kerwin Yoder
 * @version 2016.05.01
 */
public class HuffmanEncoder {

    HuffmanTree<Character> tree;
    Map<Character, String> codes;
    char[] frequencies;

    public HuffmanEncoder(char[] frequencies) {
        this.frequencies = frequencies;
        initializeTree();
        codes = tree.getMap();

    }

    public HuffmanEncoder(String filename) {
        frequencies = new char[256];
        analyzeFrequencies(filename);
        initializeTree();
        codes = tree.getMap();
    }

    public String encode(String string) {
        StringBuilder builder = new StringBuilder();
        int length = string.length();
        char[] buffer = string.toCharArray();
        for (int i = 0; i < length; ++i) {
            builder.append(codes.get(buffer[i]));
            char d = buffer[i];
        }
        return builder.toString();
    }

    private void analyzeFrequencies(String filename) {
        try {
            char[] buffer = new char[1024];
            int bytesRead;
            int index;
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            bytesRead = reader.read(buffer);
            while (bytesRead != -1) {
                for (int i = 0; i < bytesRead; ++i) {
                    index = (int) buffer[i];
                    if (index > 255) {
                        System.out.printf("The file %s contains non-ASCII characters.", filename);
                        System.exit(1);
                    }
                    frequencies[(int) buffer[i]]++; //?????
                }
                bytesRead = reader.read(buffer);
            }
        } catch (FileNotFoundException ex) {
            System.out.println(String.format("The file %s could not be found.%n", filename));
            System.exit(1);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void initializeTree() {
        if (frequencies == null) {
            throw new IllegalStateException("Attempted to initialize the tree before the frequency table was initialized.");
        }
        PriorityQueue<HuffmanTree<Character>> queue = new PriorityQueue<>();
        int length = frequencies.length;
        int frequency;
        //add all of the characters with frequencies greater than zero to the queue
        for (int i = 0; i < length; ++i) {
            frequency = frequencies[i];
            if (frequency != 0) {
                queue.insert(new HuffmanTree<>((char) i, frequency), frequency);
            }
        }
        HuffmanTree<Character> left;
        HuffmanTree<Character> right;
        while (queue.size() != 1) {
            left = queue.remove();
            right = queue.remove();
            HuffmanTree<Character> newTree = new HuffmanTree(left, right);
            queue.insert(newTree, newTree.getPriority());
        }
        tree = queue.remove();
    }
}
