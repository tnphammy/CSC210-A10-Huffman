import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Queue;

/** Class to perform decoding on an encoded text using Huffman Coding */
public class HuffDecode {
    public static void main(String[] args) throws FileNotFoundException {
        // 1. Uses codeFile (a "dictionary/manual") 
        String filePath = "DefaultTree.txt";
        // Make the corresponding Huffman tree
        HuffTree tree = HuffTree.readHuffTree(filePath);

        // 2. Reads encoded text
        String resultString = "";
        String encodedText = "";
        String encodedPath = "encoded.txt";
        try (Scanner reader = new Scanner(new File(encodedPath))) {
            while (reader.hasNextLine()) {
                encodedText = reader.nextLine();
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        }
        // make a queue representation of encoded text
        Queue<String> encodedQ = new LinkedList<String>();
        for (char c : encodedText.toCharArray()) {
            encodedQ.add(String.valueOf(c)); 
        }
        // ------------------The meat of the operation------------- 
        // 0. Follow path until it hits a leaf -> return leaf data, add to result
        String decoding = ""; /* the potential decodable string */
        while (!encodedQ.isEmpty()) {
            decoding += encodedQ.poll(); /* get/remove first element of encoded text */
            HuffTree trace = tree; // always start at the root
            HuffTree resNode = trace.followPath(decoding);
            if (resNode.isLeaf()) {
                resultString += resNode.getData();
                decoding = ""; /* reset */
            }

        }
        // 3. Output decoded text
        System.out.println("your decoded string is: " + resultString);

        
    }
}
