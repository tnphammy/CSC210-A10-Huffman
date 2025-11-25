# A10 - Huffman Coding

## Overview

For this assignment, you will create a program that can read and write text expressed using a variable bitrate encoding.  
Such schemes compress files using an algorithm that consumes fewer resources for both storage and transmission.

The canonical variable bitrate algorithm is called Huffman coding after its inventor, David Huffman, who came up with the technique and proved its optimality while still a student, in response to a homework assignment.  
You can read more about the algorithm in its [Wikipedia article](http://en.wikipedia.org/w/index.php?title=Huffman_coding&oldid=578836265).  
Basically, the approach uses short bit strings to encode common characters.  
Rarer characters are encoded with much longer bit strings.  
The savings on the short, common characters more than makes up for the extra bits used for the rare, long characters.

To avoid confusion, Huffman coding uses a prefix code scheme.  
Here, any sequence used to encode a character cannot be used to encode the beginning sequence of another character.  
So for example, if the code 00 represents the letter e, then no other code can begin with 00 -- they must all start with 01, 10, or 11 instead.  
This ensures that the encoded bit string has a unique interpretation.  
It also means that the encoding scheme can be conveniently represented using a binary tree, with symbols at the leaves.  
The path used to reach that symbol gives the code that represents that symbol.

By convention, left branches correspond to a 0 in the code, and right branches to a 1.  
Our example of 00 = e would be encoded as the left child of the left child of the root (left-left from root).  
Similarly, if the leaf node for the letter t is reached by going left-right-left from the root, then the code for t would be 010.

## Getting Started : HuffTree Data Structure  
This assignment will build on a `HuffTree` class that can represent a prefix code.  
You will write this class first, but you should try to design it so that it can be used in later parts of the assignment.  
It is best if you can implement HuffTree as a subclass of BinaryTree<Character>, so that you inherit all the methods we defined there.  
You will likely want to re-use some of your code from prior Tree assignments this semester, including your strategy for implementing subclasses like `DecisionTree` and `BST`, as well as something like the `followPath` method from `DecisionTree`.  
<!-- is there any trick to this, or is it similar to what they've done the past two assignments?-->  
<!-- I think the root should always be null and we should always skip it?-->

### Reading a Tree File

You will need a static method that allows you to read a simple text file and returns the tree structure that is represented.  
Call this `readHuffTree`.  
The format of the code files is very simple.  
Each line consists of a bit pattern (the code) in zeros and ones, followed by a number between 0 and 255 representing the ASCII value of the character it represents.  
You should interpret the bit pattern as a path to a particular node in a tree (0 = left, 1 = right), and the character as the value to be stored at the node, similar to how things worked with the decision tree file.  
Here's a mini-example: the code here would represent the tree pictured below.  
<!--If you can see this comment: ignore the <br> in the lines below. It is markdown formatting that allows them to render line by line.-->

0 65 <br>  
10 66 <br>  
11 67

![abc_tree.png](abc_tree.png)

When reading in the code, build the tree by traveling to the left or right according to the bit pattern.  
You may need to create new nodes (as needed) while you traverse:  unlike the decision tree, here only the leaves are specified.  
(You may fill these intermediate nodes with null characters, `0`.)  
When you get to the end of the pattern you are trying to encode, you should be at a new leaf node.  
Store the character value in the data field of that node.

Note: If you read the value as an int `v`, you can cast it to type `char` as follows: `(char)v`.

## Executable Classes
Once you have finished the `HuffTree` class, you will write short programs to perform tasks related to Huffman coding.  
Each class will have its own main method so that it can be run independently.  
They are described in the sections below.  
Only the first two need to be completed for the standard assignment.  
The third (building a Huffman tree from frequency counts) is optional for kudos.

### 1. Huffman Decoding

The first program, `HuffDecode`, will read a code from a file and build a Huffman tree from it (more on this below).  
It will then use the tree to convert a file from code into plaintext.  
The method you write to decode the message can be either recursive or iterative.

The command to run the program will look as follows:  
`java HuffDecode codeFile < encoded.txt > decoded.txt`  
(If you need a refresher on file redirection: here, the file encoded.txt is being sent to your program as System.in, and the output your program writes to System.out gets stored in decoded.txt.  
All this is done by the operating system as a result of the command above, without the need for your program to specify it. See [here](https://www.science.smith.edu/~nhowe/teaching/csc210/Tutorials/redir.php) for more on input redirection, and [here](https://www.science.smith.edu/~nhowe/teaching/csc210/Tutorials/cygwin.php) if you want to do this on Windows.)

To implement the decoding, your program will need to travel down the tree based on the input bits.  
Each time it reaches a leaf in the tree, it should emit the character stored at the leaf, and then return to the root.

When your program is running correctly, you can use this code file to decode encoded text.  
What is the secret message? Show the answer in your typescript.

### 2. Huffman Encoding

The second program, `HuffEncode`, is perhaps the simplest, and doesn't even need a tree.  
It will read a code file and then convert plaintext to encoded text.  
You can do this with a simple lookup table or hash map -- at the index of the character to be encoded, you will store the corresponding code.

The command to run the program will look as follows:  
`java HuffEncode codeFile < original.txt > encoded.txt`

If you have written the two programs above correctly, you should be able to pipe them together as shown below:  
`java HuffEncode codeFile < original.txt | java HuffDecode codeFile > decoded.txt`

In the example above, `decoded.txt` should come out identical to `original.txt`.

### Kudos: Building a Tree From Data

The third program (optional) will read a plaintext file, count the frequencies of the characters that occur, and use them to build a tree according to Huffman's algorithm.  
It will then output a code file of the sort used by the two programs above.

`java BuildHuffTree codeFile < sampleText`

Similar to the method you wrote above to read in a file, you will need to add a method to write your tree out to a file.  
When you have completed this portion, you should also carry out the analysis experiment described in the section below.

Your encoding scheme should be able to encode any 8-bit ASCII character.  
When processing the text file to build the tree, keep a count of the number of appearances of every character code from 0 to 255.  
You will use these counts to build the tree.  
Ignore any characters with values greater than 255.

Notes

For simplicity, the encoded files will record each bit as a character '0' or '1', which is actually represented in ASCII code using 8 bits! <!-- I don't quite understand what this is saying-->  
This makes the encoded files easy to read in a text editor. However, it does not decrease the file size since we are using 8 times the number of bits that are actually needed. We will overlook this small problem in favor of the convenience of a human-readble output. If you really wanted to use a Huffman encoding to save space, you would have to manipulate individual bits and read and write the encoded data as a binary file, but that's not necessary for the main assignment. (See extra credit below.)

### Double Kudos: Analysis

How much difference does the specific encoding tree make in the compression rate?  
You can explore this question for kudos/fun.  
Consider three files: a short test message ("This is a test of Huffman coding."), a dictionary (use the word list from A8), and [Tolstoy's War and Peace](https://archive.org/stream/warandpeace030164mbp/warandpeace030164mbp_djvu.txt).  


Use each file as the source text to build a tree.  
Then encode each file using each tree.  
Make a table of the compression rates (bits/character) in your README.  
For each document, which code has the best compression rate?  
Which source's code offers the best average compression rate over all three documents?  
Describe your observations.  
Are there any other interesting patterns?  
What do you conclude?

### Triple Kudos: Bit Encoding

As further extra credit, you may make a new program, modified from the original, that reads and writes binary coded files.  
These should be 1/8 the size of the files written using ASCII digits.  
A file that is encoded and decoded using your programs should be exactly identical to the original.  
Only attempt this one if you have already completed all three programs above, plus the first extra credit.

## To Submit

   `HuffTree.java`  
   `HuffEncode.java` to encode files  
   `HuffDecode.java` to decode files  
   `readme.md`  
 
   For kudos: 
   `BuildHuffTree.java` to create a tree from data
      `HuffBitEncode.java` and `HuffBitDecode.java` to do bit compression


