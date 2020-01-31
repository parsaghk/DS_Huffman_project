package ir.ac.kntu;

import java.io.*;
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
            String subCode;
            for (int i = 0; i < code.length() - 7; i += 8) {
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
            String code = "";
//            code = new String(bytes , StandardCharsets.ISO_8859_1);
            for (int i = 0 ;i<bytes.length;i++){
                byte c = bytes[i];
//                System.out.println(code);
//                code += String.format("%8s",Integer.toBinaryString(c & 0xFF).replace(' ','0'));
//                System.out.println(Integer.toBinaryString(c));


            }
            String s = new String(bytes);
            System.out.println(s);
//            System.out.println(code);




        } catch (IOException e) {
            e.printStackTrace();
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
}
