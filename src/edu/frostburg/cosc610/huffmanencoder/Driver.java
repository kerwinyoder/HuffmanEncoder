package edu.frostburg.cosc610.huffmanencoder;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * The Driver for the HuffmanEncoder
 *
 * @author Kerwin Yoder
 * @version 2016.05.01
 */
public class Driver {

    private static final String OUTPUT = "./output.txt";
    private static final String STATISTICS = "./statistics.txt";

    /**
     * The main method for the Driver
     *
     * @param args the command line arguments; exactly one is required: the
     * input filename
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Invalid number of command line arguments. Please specify only the input filename.");
            System.exit(1);
        }
        String filename = args[0];
        try (BufferedReader reader = new BufferedReader(new FileReader(filename));
                BufferedWriter codeWriter = new BufferedWriter(new FileWriter(OUTPUT));
                BufferedWriter statWriter = new BufferedWriter(new FileWriter(STATISTICS))) {
            HuffmanEncoder encoder = new HuffmanEncoder(filename);
            String coded;
            int inputSize = 0;
            int outputSize = 0;
            int bytesRead;
            char[] buffer = new char[1024];
            bytesRead = reader.read(buffer);
            while (bytesRead != -1) {
                inputSize += bytesRead;
                coded = encoder.encode(buffer, bytesRead);
                outputSize += coded.length();
                codeWriter.write(coded);
                bytesRead = reader.read(buffer);
            }
            //convert outputSize from number of bits to number of bytes
            outputSize = outputSize % 8 == 0 ? outputSize / 8 : outputSize / 8 + 1;
            statWriter.write(String.format("Input size: %d bytes%n", inputSize));
            statWriter.write(String.format("Output size: %d bytes%n%n", outputSize));
            statWriter.write(encoder.getStatistics());
        } catch (FileNotFoundException ex) {
            System.out.printf("The file %s could not be found.%n", filename);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
