import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InaccessibleObjectException;
import java.util.Scanner;

/**
 * Class that implements a Huffman Coding Node
 * 
 * @author Tammy Pham
 */
public class HuffTree extends BinaryTree<Character> {

    /** The value stored by the node */
    Character data;
    /** The node to the left */
    HuffTree left;
    /** The node to the left */
    HuffTree right;

    /**
     * Basic constructor to make a leaf node
     * 
     * @param data
     */
    public HuffTree(Character data) {
        super(data);
    }

    /**
     * Three-valued constructor to make a Node
     * 
     * @param data
     * @param left
     * @param right
     */
    public HuffTree(Character data, HuffTree left, HuffTree right) {
        super(data, left, right);
    }

    /** Accessor for node data */
    public Character getData() {
        if (super.getData() instanceof Character) {
            return super.getData();
        } else {
            throw new UnsupportedOperationException("The data entered is not of type String.");
        }
    }

    /** Accessor for left child */
    public HuffTree getLeft() {
        return (HuffTree) super.getLeft();
    }

    /** Accessor for right child */
    public HuffTree getRight() {
        return (HuffTree) super.getRight();
    }

    /** Manipulator for node data */
    public void setData(Character data) {
        super.setData(data);
    }

    /** Manipulator for left child */
    public void setLeft(HuffTree left) {
        if (left == null || left instanceof HuffTree) {
            super.setLeft(left);
        } else {
            throw new UnsupportedOperationException();
        }
    }

    /** Manipulator for right child */
    public void setRight(HuffTree right) {
        if (right == null || right instanceof HuffTree) {
            super.setRight(right);
        } else {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * Follows a string representation of a path containing 0's and 1's and returns
     * the corresponding node
     * 
     * @param path The direction to the node
     * @return the resulting node
     */
    public HuffTree followPath(String path) {
        HuffTree curr = this;
        // traverse the tree
        for (int i = 0; i < path.length(); i++) {
            String direction = String.valueOf((path.charAt(i))).toUpperCase();
            if (direction.equals("0")) {
                curr = curr.getLeft();
            } else if (direction.equals("1")) {
                curr = curr.getRight();
            } else {
                throw new InaccessibleObjectException("The node you're trying to reach does not exist.");
            }
        }
        return curr;
    }

    /**
     * Produces a Huffman Tree by reading a text file
     * 
     * @param file the text file
     * @return the resulting Huffman Tree
     */
    public static HuffTree readHuffTree(String file) {
        HuffTree root = new HuffTree('0');
        System.out.println("root is: " + root);
        // Reads file to make tree
        String filePath = file;
        try (Scanner reader = new Scanner(new File(filePath))) {
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                root.readLine(line, root);
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        }
        System.out.println("root now: " + root);
        return root;
    }

    /**
     * Produces a node by reading an individual line of directions
     * 
     * @param line the directions
     * @return the resulting node
     */
    public HuffTree readLine(String line, HuffTree root) {
        // Base Case
        char[] lineArr = line.toCharArray();
        if (lineArr[0] == ' ') {
            // Get node value
            int val = Integer.parseInt(line.trim());
            char charVal = (char) val;
            // Set node value
            this.setData(charVal);
            System.out.println("charVal: " + charVal);
        }
        // Recursive Step
        else if (lineArr[0] == '0') {
            // Check if left exists
            if (this.getLeft() == null) {
                this.setLeft(new HuffTree('0')); /* Create a left node */
            }
            this.getLeft().readLine(line.substring(1), root);
        } else if (lineArr[0] == '1') {
            // Check if right exists
            if (this.getRight() == null) {
                this.setRight(new HuffTree('0')); /* Create a right node */
            }
            this.getRight().readLine(line.substring(1), root);
        }
        return root;
    }

    public static void main(String[] args) {
        String filePath = "DefaultTree.txt";
        readHuffTree(filePath);
    }
}