package ir.ac.kntu;

import java.util.HashMap;

public class HuffmanTree {

    private HuffmanNode[] nodes;
    HashMap<Integer, String> mapCodes;
    HuffmanNode root;

    public HuffmanTree(HuffmanNode[] nodes) {
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
            mapCodes.put(root.character, s);
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


}
