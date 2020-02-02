package ir.ac.kntu;

import java.io.*;
import java.nio.Buffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class HuffmanTree {

    private HuffmanNode[] nodes;
    HashMap<String, Integer> mapCodes; //String : code , Integer:ascii
    HashMap<Integer, String> codeMap;
    HuffmanNode root;

    public HuffmanTree(HuffmanNode[] nodes) {
        codeMap = new HashMap<>();
        mapCodes = new HashMap<>();
        this.nodes = nodes;
        sortNodesByData();
        buildHuffmanTree();
        root = nodes[0];
        buildCode(root, "");
    }

    public void buildCode(HuffmanNode root, String s) {
        System.out.println(root.character + " " + root.data);
        if (root.left
                == null
                && root.right
                == null) {
            mapCodes.put(s, root.character);
            codeMap.put(root.character, s);
        }
        // c is the character in the node
        if (root.right != null)
            buildCode(root.right, s + "1");
        if (root.left != null)
            buildCode(root.left, s + "0");


    }

    public void sortNodesByData() {

        for (int i = 0; i < nodes.length; i++) {

            for (int j = 0; j < nodes.length - 1; j++) {

                if (nodes[j + 1] != null) {
                    if (nodes[j].data < nodes[j + 1].data) {
                        HuffmanNode helper;
                        helper = nodes[j + 1];
                        nodes[j + 1] = nodes[j];
                        nodes[j] = helper;


                    }
                }

            }
        }
    }

    public void printArray() {

        for (int i = 0; i < nodes.length; i++) {
            if (nodes[i] != null) {
                System.out.println(nodes[i].character + " " + nodes[i].data);
            }
        }

    }

    public void buildHuffmanTree() {

        HuffmanNode right;
        HuffmanNode left = null;
        HuffmanNode root;
        int counter = nodes.length - 1;
        while (nodes[1] != null) {

            left = nodes[counter];
            right = nodes[counter - 1];

            root = new HuffmanNode(128, right.data + left.data, left, right);

            nodes[counter] = null;
            nodes[counter - 1] = root;

            sortNodesByData();
            counter--;
            printArray();
            System.out.println("--------");


        }


    }


    public void compressCode() {
        try {

            BufferedReader reader = new BufferedReader(new FileReader("./src/ir/ac/kntu/text.txt"));
            FileOutputStream writer = new FileOutputStream("./src/ir/ac/kntu/comp.txt");
            String code = "";
            int ascii;
            while ((ascii = reader.read()) != -1) {
                code += findCode(ascii);


            }
            writer.write((byte) code.length());
            String subCode;
            for (int i = 0; i < code.length(); i += 8) {
                subCode = code.substring(i, Math.min(code.length(), i + 8));
                writer.write((byte) Integer.parseInt(subCode, 2));

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }

    }

    public void addHeader() {

        try {
            FileOutputStream writer = new FileOutputStream("./src/ir/ac/kntu");

            for (Map.Entry mapElement : codeMap.entrySet()) {
                writer.write((byte) mapElement.getKey());
                String str = (String) mapElement.getValue();
                String subStr = "";
                for (int i = 0; i < str.length() - 7; i += 8) {
                    subStr = str.substring(i, Math.min(str.length(), i + 8));
                    writer.write((byte) Integer.parseInt(subStr, 2));
                    int star = (int) '*';
                    writer.write((byte) star);

                }

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String findCode(int character) {

        return codeMap.get(character);

    }

    public void extractCode() {
        try {
            Path path = Paths.get("./src/ir/ac/kntu/comp.txt");
            byte[] bytes = Files.readAllBytes(path);
            String zeros = "";
            int byteToInt;
            byte tempByte;
            String subString;
            int i;
            int strlength = bytes[0];
            for (i = 1; i < bytes.length - 1; i++) {
                tempByte = bytes[i];
                byteToInt = tempByte;
                subString = Integer.toBinaryString(byteToInt);
                if (byteToInt < 0) {
                    subString = subString.substring(24, 32);
                }
                for (int j = 0; j < 8 - subString.length(); j++) {
                    zeros += "0";
                }
                zeros = zeros + subString;
            }
            tempByte = bytes[i];
            byteToInt = tempByte;
            subString = Integer.toBinaryString(byteToInt);
            if (byteToInt < 0) {
                subString = subString.substring(24, 32);
            }
            if (strlength % 8 == 0) {
                for (int j = 0; j < 8 - subString.length(); j++) {
                    zeros += "0";
                }
            } else {
                if (strlength % 8 > subString.length()) {
                    for (int j = 0; j < strlength % 8 - subString.length(); j++) {
                        zeros += "0";
                    }
                } else {
                    zeros = zeros + subString;
                }
            }
            writeExtractCode(zeros);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeExtractCode(String zeros) {

        try {
            FileWriter writer = new FileWriter("./src/ir/ac/kntu/extract.txt");

            String code = "";
            for (int i = 0; i < zeros.length(); i++) {
                code += Character.toString(zeros.charAt(i));
                if (mapCodes.containsKey(code)) {
                    int ch = mapCodes.get(code);
                    char character = (char) ch;
                    writer.write(Character.toString(character));
                    code = "";
                }

            }
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void compressedText() {

        try {
            BufferedReader reader = new BufferedReader(new FileReader("./src/ir/ac/kntu/text.txt"));
            BufferedWriter writer = new BufferedWriter((new FileWriter("./src/ir/ac/kntu/compressed.txt")));

            int ascii;
            while ((ascii = reader.read()) != -1) {
                writer.write(findCode(ascii));
            }

            writer.close();

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }


    }

    public void byteCompressing() {

        try {
            FileInputStream input = new FileInputStream("./src/ir/ac/kntu/compressed.txt");
            FileOutputStream output = new FileOutputStream("./src/ir/ac/kntu/compressedByte.txt");

            while (true) {


            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }


//    void convertMaptoCode(){
//
//
//        String code="";
//        mapCodes.entrySet().forEach(entry ->{
//
//            code+= entry.getKey() + " " + entry.ge
//        });
//
//
//    }
//
//    void writeMapcode(){}

}
