package ir.ac.kntu;

import java.io.*;
import java.util.HashMap;

public class HuffmanTree {

    private HuffmanNode[] nodes;
    HashMap<String, Integer> mapCodes;
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


    public void writeCode() {
        try {

            FileReader reader = new FileReader("./src/ir/ac/kntu/text.txt");
            FileWriter writer = new FileWriter("./src/ir/ac/kntu/compressed.txt");


            int ascii;
            while ((ascii = reader.read()) != -1) {
                writer.write(findCode(ascii));


            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }

    }

    public String findCode(int character) {

        return codeMap.get(character);

    }

    public void extractCode() {
        try {
            FileInputStream reader = new FileInputStream("./src/ir/ac/kntu/compressed.txt");
            FileOutputStream writer = new FileOutputStream("./src/ir/ac/kntu/extract.txt");
            String ascii = "";
            int num;
            while ((num = reader.read()) != -1) {
                char c = (char) num;
                ascii += Character.toString(c);
                System.out.println(ascii);
                if (mapCodes.containsKey(ascii)) {

                    int ch = mapCodes.get(ascii);
                    System.out.println(ch);
                    char character = (char) ch;

                    System.out.println(character);
                    writer.write(Character.toString(character));
                    ascii = "";

                }


            }

            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    public void byteSavaing (){

        try {
            FileInputStream input = new FileInputStream("./src/ir/ac/kntu/compressed.txt");
            FileOutputStream output= new FileOutputStream("./src/ir/ac/kntu/compressedByte.txt");

            while(true){


            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }



    }
}
