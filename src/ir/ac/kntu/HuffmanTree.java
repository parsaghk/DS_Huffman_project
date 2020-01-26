package ir.ac.kntu;

import java.lang.reflect.Array;
import java.util.HashMap;

public class HuffmanTree {

    private HuffmanNode[] nodes;
    private HuffmanNode root;

    public HuffmanTree(HuffmanNode[] nodes) {
        this.nodes = nodes;
        this.root = null;
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





}
