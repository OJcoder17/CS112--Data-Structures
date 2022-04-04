package huffman;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;

/**
 * This class contains methods which, when used together, perform the
 * entire Huffman Coding encoding and decoding process
 * 
 * @author Ishaan Ivaturi
 * @author Prince Rawal
 */
public class HuffmanCoding {
    private String fileName;
    private ArrayList<CharFreq> sortedCharFreqList;
    private TreeNode huffmanRoot;
    private String[] encodings;

    /**
     * Constructor used by the driver, sets filename
     * DO NOT EDIT
     * @param f The file we want to encode
     */
    public HuffmanCoding(String f) { 
        fileName = f; 
    }

    /**
     * Reads from filename character by character, and sets sortedCharFreqList
     * to a new ArrayList of CharFreq objects with frequency > 0, sorted by frequency
     */
    public void makeSortedList() {
        StdIn.setFile(fileName);

        this.sortedCharFreqList = new ArrayList<CharFreq>();
        int[] array = new int[128];
        int c = 0;
        
        while(StdIn.hasNextChar() != false){
            
            array[StdIn.readChar()] += 1;
            c++;
        }
        
        for(int i = 0; i < array.length; i++){
            if(array[i] == 0){
            
            }else{
                this.sortedCharFreqList.add(new CharFreq((char)i, (double)array[i]/c));
            }
        }
        if(this.getSortedCharFreqList().size() == 1){
            char b = (char)((int)this.sortedCharFreqList.get(0).getCharacter()+1);
            this.sortedCharFreqList.add(new CharFreq(b,0));
        }
        Collections.sort(this.sortedCharFreqList);
        
        System.out.println(this.sortedCharFreqList);
    }

    /**
     * Uses sortedCharFreqList to build a huffman coding tree, and stores its root
     * in huffmanRoot
     */
    public void makeTree() {

        Queue<TreeNode> src= new Queue<>(), target = new Queue<TreeNode>();
       
        for(CharFreq CharFreq: sortedCharFreqList)
           
        src.enqueue(new TreeNode(CharFreq, null, null));

        TreeNode left, right;
       
        while (!src.isEmpty()){
            left = dequeue_smaller(src, target);
            right = dequeue_smaller(src, target);
            if (left != null && right != null){
                TreeNode combined_node = add_tree_nodes(left, right);
                target.enqueue(combined_node);
            }
            else if (left != null)
                target.enqueue(left);
            else
                target.enqueue(right);
        }
        while (target.size() > 1){
            left = target.dequeue();
           
            right = target.dequeue();
         
            TreeNode combined_node = add_tree_nodes(left, right);
            target.enqueue(combined_node);
        }
        huffmanRoot = target.dequeue();
    }

    private static TreeNode dequeue_smaller(Queue<TreeNode> q1, Queue<TreeNode> q2){
        TreeNode one = null;
        
        TreeNode two = null;
        if(!q1.isEmpty()){
            one = q1.peek();
        }
        if(!q2.isEmpty()){
            two = q2.peek();
        }
        TreeNode result;
       
        if (one != null && two != null){
            if (one.getData().getProbOcc()<=two.getData().getProbOcc())
                result = q1.dequeue();
            else
                result = q2.dequeue();
        }
        else if (one != null)
            result = q1.dequeue();
       
            else if (two != null)
          
            result = q2.dequeue();
        else
            result = null;
        return result;
    }

    private static TreeNode add_tree_nodes(TreeNode left, TreeNode right){
   
        double added_frequency = left.getData().getProbOcc() + right.getData().getProbOcc();
          
        CharFreq combined_CharFreq_node = new CharFreq(null, added_frequency);
        
        TreeNode combined_node = new TreeNode(combined_CharFreq_node, left, right);
        return combined_node;
    }
    

    /**
     * Uses huffmanRoot to create a string array of size 128, where each
     * index in the array contains that ASCII character's bitstring encoding. Characters not
     * present in the huffman coding tree should have their spots in the array left null.
     * Set encodings to this array.
     */
    public void makeEncodings() {

        encodings = new String[128];
      
        ArrayList<String> Codes = new ArrayList<>(); 
        
        ArrayList<Character> characters = new ArrayList<>(); 
       
        encoding(Codes, characters, huffmanRoot, ""); 
        for (int i = 0; i < Codes.size(); i++){ 
            encodings[characters.get(i)] = Codes.get(i); 
        }
    }

    private void encoding(ArrayList<String> Codes, ArrayList<Character> characters, TreeNode current, String str) {
        if (current.getLeft() == null && current.getRight() == null) { 
            
            Codes.add(str); 
            characters.add(current.getData().getCharacter()); 
            return;
        }
        encoding(Codes, characters, current.getLeft(), str + "0"); 
        
        encoding(Codes, characters, current.getRight(), str + "1"); 
    }
    /**
     * Using encodings and filename, this method makes use of the writeBitString method
     * to write the final encoding of 1's and 0's to the encoded file.
     * 
     * @param encodedFile The file name into which the text file is to be encoded
     */
    public void encode(String encodedFile) {
        StdIn.setFile(fileName);

        StdIn.setFile(fileName);
 
        String bits = "";
        while (StdIn.hasNextChar()){ 
            bits += "" + encodings[StdIn.readChar()];
        }
        writeBitString(encodedFile, bits); 
    }
    
    /**
     * Writes a given string of 1's and 0's to the given file byte by byte
     * and NOT as characters of 1 and 0 which take up 8 bits each
     * DO NOT EDIT
     * 
     * @param filename The file to write to (doesn't need to exist yet)
     * @param bitString The string of 1's and 0's to write to the file in bits
     */
    public static void writeBitString(String filename, String bitString) {
        byte[] bytes = new byte[bitString.length() / 8 + 1];
        int bytesIndex = 0, byteIndex = 0, currentByte = 0;

        // Pad the string with initial zeroes and then a one in order to bring
        // its length to a multiple of 8. When reading, the 1 signifies the
        // end of padding.
        int padding = 8 - (bitString.length() % 8);
        String pad = "";
        for (int i = 0; i < padding-1; i++) pad = pad + "0";
        pad = pad + "1";
        bitString = pad + bitString;

        // For every bit, add it to the right spot in the corresponding byte,
        // and store bytes in the array when finished
        for (char c : bitString.toCharArray()) {
            if (c != '1' && c != '0') {
                System.out.println("Invalid characters in bitstring");
                return;
            }

            if (c == '1') currentByte += 1 << (7-byteIndex);
            byteIndex++;
            
            if (byteIndex == 8) {
                bytes[bytesIndex] = (byte) currentByte;
                bytesIndex++;
                currentByte = 0;
                byteIndex = 0;
            }
        }
        
        // Write the array of bytes to the provided file
        try {
            FileOutputStream out = new FileOutputStream(filename);
            out.write(bytes);
            out.close();
        }
        catch(Exception e) {
            System.err.println("Error when writing to file!");
        }
    }

    /**
     * Using a given encoded file name, this method makes use of the readBitString method 
     * to convert the file into a bit string, then decodes the bit string using the 
     * tree, and writes it to a decoded file. 
     * 
     * @param encodedFile The file which has already been encoded by encode()
     * @param decodedFile The name of the new file we want to decode into
     */
    public void decode(String encodedFile, String decodedFile) {
        StdOut.setFile(decodedFile);

	    StdOut.setFile(decodedFile);
        String str = readBitString(encodedFile);
        
        TreeNode temporary_node = huffmanRoot;
        while (0 < str.length() || temporary_node != huffmanRoot) {
            
            if (temporary_node.getLeft() == null && temporary_node.getRight() == null && temporary_node.getData().getCharacter() != 0) {
                StdOut.print(temporary_node.getData().getCharacter());
                temporary_node = huffmanRoot;
               
                continue;
            } else if (str.substring(0, 1).equals("0"))
                temporary_node = temporary_node.getLeft();
            else if (str.substring(0, 1).equals("1"))
                temporary_node = temporary_node.getRight();
            str = str.substring(1);
        }
    }

    /**
     * Reads a given file byte by byte, and returns a string of 1's and 0's
     * representing the bits in the file
     * DO NOT EDIT
     * 
     * @param filename The encoded file to read from
     * @return String of 1's and 0's representing the bits in the file
     */
    public static String readBitString(String filename) {
        String bitString = "";
        
        try {
            FileInputStream in = new FileInputStream(filename);
            File file = new File(filename);

            byte bytes[] = new byte[(int) file.length()];
            in.read(bytes);
            in.close();
            
            // For each byte read, convert it to a binary string of length 8 and add it
            // to the bit string
            for (byte b : bytes) {
                bitString = bitString + 
                String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0');
            }

            // Detect the first 1 signifying the end of padding, then remove the first few
            // characters, including the 1
            for (int i = 0; i < 8; i++) {
                if (bitString.charAt(i) == '1') return bitString.substring(i+1);
            }
            
            return bitString.substring(8);
        }
        catch(Exception e) {
            System.out.println("Error while reading file!");
            return "";
        }
    }

    /*
     * Getters used by the driver. 
     * DO NOT EDIT or REMOVE
     */

    public String getFileName() { 
        return fileName; 
    }

    public ArrayList<CharFreq> getSortedCharFreqList() { 
        return sortedCharFreqList; 
    }

    public TreeNode getHuffmanRoot() { 
        return huffmanRoot; 
    }

    public String[] getEncodings() { 
        return encodings; 
    }
}
