package huffmanencodertests;

import edu.frostburg.cosc610.huffmanencoder.HuffmanEncoder;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * A test class for the HuffmanEncoder
 *
 * @author Kerwin Yoder
 * @version 2016.05.01
 */
public class HuffmanEncoderTest {

    private static final String INPUT = "../input.txt";

    public static void main(String[] args) {
        try {
            HuffmanEncoder encoder = new HuffmanEncoder(INPUT);
            StringBuilder builder = new StringBuilder();
            Scanner scanner = new Scanner(new File(INPUT));
            while (scanner.hasNextLine()) {
                builder.append(scanner.nextLine());
            }
            String encoded = encoder.encode(builder.toString());
            System.out.println(encoded);
        } catch (FileNotFoundException ex) {
            System.out.printf("The file %s could not be found.%n", INPUT);
        }
    }
}
