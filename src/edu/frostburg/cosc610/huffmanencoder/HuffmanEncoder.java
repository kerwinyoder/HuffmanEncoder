package edu.frostburg.cosc610.huffmanencoder;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

/**
 * A Huffman Encoder for ASCII Characters (including the extended ASCII codes).
 * Encoded data is returned as a string of bits rather than a byte stream.
 *
 * @author Kerwin Yoder
 * @version 2016.05.01
 */
public class HuffmanEncoder {

    HuffmanTree<Character> tree;
    Map<Character, String> codes;
    int[] frequencies;

    /**
     * Creates a new HuffmanEncoder using the given frequencies table. The
     * frequency for each ASCII character is assumed to be stored at the index
     * of the characters ASCII code value (e.g. the frequency of 'A' is at 65)
     *
     * @param frequencies the frequencies table
     */
    public HuffmanEncoder(int[] frequencies) {
        this.frequencies = frequencies;
        initializeTree();
        codes = tree.getMap();
    }

    /**
     * Creates a new HuffmanEncoder with frequencies based on the data in the
     * file with the given filename
     *
     * @param filename the name of the file used for frequency analysis
     */
    public HuffmanEncoder(String filename) {
        frequencies = new int[256];
        analyzeFrequencies(filename);
        initializeTree();
        codes = tree.getMap();
    }

    /**
     * Encodes the given string. This is a convenience method for encode(char[]
     * characters).
     *
     * @param string the string to encode
     * @return the encoded string
     */
    public String encode(String string) {
        if (string == null) {
            return null;
        }
        return encode(string.toCharArray(), string.length());
    }

    /**
     * Encodes the given char[]
     *
     * @param characters the array of chars to encode
     * @return the encoded string
     */
    public String encode(char[] characters) {
        return encode(characters, characters.length);
    }

    /**
     * Encodes the first chars of the given char[] up to the given endIndex
     *
     * @param characters the array of chars to encode
     * @param endIndex the ending index (exclusive) of chars that will be
     * encoded
     * @return the encoded string
     */
    public String encode(char[] characters, int endIndex) {
        if (characters == null) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < endIndex; ++i) {
            builder.append(codes.get(characters[i]));
        }
        return builder.toString();
    }

    /**
     * Gets a string representation of the frequency table
     *
     * @return a string representation of the frequency table
     */
    public String getStatistics() {
        if (frequencies == null) {
            throw new IllegalStateException("Attempted to get the frequency table string representation before it was initialized.");
        }
        StringBuilder builder = new StringBuilder();
        //use string literals to force correct widths for alignment purposes
        builder.append(String.format("Statistics%n%n%5s%7s%16s%17s%n", "ASCII", "Char", "Code", "Frequency"));
        int frequency;
        int length = frequencies.length;
        for (int i = 0; i < length; ++i) {
            frequency = frequencies[i];
            if (frequency != 0) {
                //do not print the horizontal tab, new line (LF) and vertical tab characters since they distort the output string
                if (i == 9 || i == 10 || i == 13) {
                    builder.append(String.format("%5d%27s%13d%n", i, codes.get((char) i), frequency));
                } else {
                    builder.append(String.format("%5d%7c%20s%13d%n", i, (char) i, codes.get((char) i), frequency));
                }
            }
        }
        return builder.toString();
    }

    /*
     * Analyzes the frequencies of characters in the file with the given filename. Only ASCII characters are allowed
     * @param filename the filename of the file to be analyzed
     */
    private void analyzeFrequencies(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            char[] buffer = new char[1024];
            int bytesRead;
            int index;
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

    /*
     * Initializes the tree based on the frequency table
     */
    private void initializeTree() {
        if (frequencies == null) {
            throw new IllegalStateException("Attempted to initialize the tree before the frequency table was initialized.");
        }
        PriorityQueue<HuffmanTree<Character>> queue = new PriorityQueue<>(100);
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
