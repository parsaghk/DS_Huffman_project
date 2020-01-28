package ir.ac.kntu;

import java.lang.reflect.Array;
import java.util.HashMap;

public class HuffmanTree {

    private HuffmanNode[] nodes;
    private HuffmanNode root;

    public HuffmanTree(HuffmanNode[] nodes) {

        this.nodes = nodes;
        this.root = null;
        sortNodesByData();
    }

    public static void printCode(HuffmanNode root, String s) {

        if (root.left
                == null
                && root.right
                == null
                && Character.isLetter(root.character)) {

            // c is the character in the node
            System.out.println(root.character + ":" + s);


        }
    }

    public void sortNodesByData() {

        for (int i = 0; i < nodes.length; i++) {

            for (int j = 0; j < nodes.length - 1; j++) {

                if (nodes[j].data < nodes[j + 1].data) {
                    HuffmanNode helper;
                    helper = nodes[j + 1];
                    nodes[j + 1] = nodes[j];
                    nodes[j] = helper;


                }
            }

        }
    }

    public void printArray() {

        for (int i = 0; i < nodes.length; i++) {

            System.out.println(nodes[i].character + " " + nodes[i].data);
        }

    }

    public void BuildHuffmanTree(){

        HuffmanNode right;
        HuffmanNode left;
        HuffmanNode root;
        int counter = nodes.length - 1;
        while(nodes[1] != null){

            left = nodes[counter];
            right = nodes[counter -1];
            


        }


    }

}
