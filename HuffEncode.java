import java.io.File;
import java.io.FileNotFoundException;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/** Class to perform encoding on a textg */
public class HuffEncode {
    public static void main(String[] args) throws FileNotFoundException {
        // 1. Uses codeFile (a "dictionary/manual") 
        String filePath = "DefaultTree.txt";
        Hashtable<String, String> dict = new Hashtable<String, String>();
        try (Scanner reader = new Scanner(new File(filePath))) {
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                Integer spaceIndex = 0;
                for (int i = 0; i < line.length(); i++) {
                    if (line.charAt(i) == ' ') {
                        String val = line.substring(0, spaceIndex);
                        String key = line.substring(spaceIndex + 1, line.length());
                        dict.put(key, val);
                        break;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        }
        // 2. Reads text
        String resultString = "";
        String inputText = "";
        try (Scanner reader = new Scanner(new File(inputText))) {
            while (reader.hasNextLine()) {
                inputText = reader.nextLine();
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        }
        for (char c : inputText.toCharArray()) {
            String decoded = dict.get(String.valueOf(c));
            resultString += decoded;
        }
        // 3. Output decoded text
        System.out.println(resultString);
 
    }
}