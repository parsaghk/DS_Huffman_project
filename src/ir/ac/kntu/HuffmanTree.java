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
            String mapcode;
            String submapcode;
            while ((ascii = reader.read()) != -1) {
                code += findCode(ascii);


            }
            mapcode = convertMaptoCode();
            String mapcodeLength = Integer.toBinaryString(mapcode.length());
            code = putCodeIn8bit(mapcodeLength) + mapcode + code;
            System.out.println("this is final code");
            System.out.println(code);
            //      writer.write((byte)mapcode.length());

//            for (int j=0;j<code.length();j+=8){
//               submapcode = mapcode.substring(j,Math.min(mapcode.length(),j+8));
//               writer.write((byte)Integer.parseInt(submapcode,2));
//
//            }
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

            System.out.println("all" + zeros);
            writeExtractCode(zeros);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeExtractCode(String zeros) {

        try {
            FileWriter writer = new FileWriter("./src/ir/ac/kntu/extract.txt");
            HashMap <String,Integer> map = new HashMap<>();
            int mapcoedeLenth =Integer.parseInt( zeros.substring(0,8),2);
            String mapcode = zeros.substring(8,8+mapcoedeLenth);
            String huffmantext=zeros.substring(8+mapcoedeLenth);
//            System.out.println("mapcode");
//            System.out.println(mapcode);
//            System.out.println("huffman code");
//            System.out.println(huffmancode);
//            System.out.println("mapcodelenth");
//            System.out.println(mapcoedeLenth);
            String huffmancode = "";
            int ascii;
            int huffmancodelenght;
            int bytes;
            int j = 0;
            while (j!=mapcoedeLenth){
                ascii = Integer.parseInt(mapcode.substring(j,j+8),2);
                huffmancodelenght = Integer.parseInt(mapcode.substring(j+8,j+16),2);
                j +=16;
                if (huffmancodelenght%8 == 0)
                    bytes = huffmancodelenght/8;
                else
                    bytes = huffmancodelenght/8 + 1;
                huffmancode = mapcode.substring(bytes*8-huffmancodelenght,bytes*8);
                j = j + bytes*8;
                map.put(huffmancode,ascii);
            }
            System.out.println("FUCK");
            System.out.println(map.toString());
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


    public String convertMaptoCode() {

        String mapString = "";
        Object[] codes = mapCodes.keySet().toArray(); //string
        Object[] asciis = mapCodes.values().toArray();//integer
        int size = codes.length;
        for (int i = 0; i < size; i++) {
            int ascii = (int) asciis[i];
            String code = (String) codes[i];
            mapString +=
                    putCodeIn8bit(Integer.toBinaryString(ascii)) +
                            putCodeIn8bit(Integer.toBinaryString(code.length())) +
                            putCodeIn8bit(code);


        }
        System.out.println("mapcode : " + mapString);
        extractMapCode(mapString);
        return mapString;

    }

    void extractMapCode(String mapcode) {
        String[] array = new String[mapcode.length() / 8];
        int counter = 0;
        String str = "";
        for (int i = 0; i < mapcode.length(); i += 8) {

            array[counter] = mapcode.substring(i, i + 8);

            counter++;
        }

        int ascii = 0;
        int count = 0;
        int j = 0;
        int huffmanCodeBytes;
        String huffmanCode = "";

        while (j != array.length) {
            ascii = Integer.parseInt(array[j], 2);
            count = Integer.parseInt(array[j + 1], 2);

            j += 2;
            if (count % 8 == 0) {
                huffmanCodeBytes = count / 8;
            } else {
                huffmanCodeBytes = count / 8 + 1;
            }
            for (int m = 0; m < huffmanCodeBytes; m++) {
                huffmanCode += array[j];
                j++;
            }
            huffmanCode = huffmanCode.substring(huffmanCodeBytes * 8 - count);

            System.out.println("acscci code :" + ascii + "   count huffman : " + count + " huffman code : " + huffmanCode);
            huffmanCode = "";
        }


    }

    private String putCodeIn8bit(String value) {

        while (value.length() % 8 != 0) {

            value = "0" + value;

        }
        return value;
    }


}
