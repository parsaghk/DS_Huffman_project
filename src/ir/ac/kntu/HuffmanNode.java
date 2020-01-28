package ir.ac.kntu;

public class HuffmanNode {

    int character;
    int data;

    HuffmanNode left;
    HuffmanNode right;

    public HuffmanNode(){
        this.right = null;
        this.left = null;
    }

    public HuffmanNode(int character, int data, HuffmanNode left, HuffmanNode right) {
        this.character = character;
        this.data = data;
        this.left = left;
        this.right = right;
    }

    public HuffmanNode(int character, int data) {
        this.right = null;
        this.left = null;
        this.character = character;
        this.data = data;
    }

    public HuffmanNode(int data) {
        this.right = null;
        this.left = null;
        this.data = data;
        this.character = ' ';
    }

    public void setLeft(HuffmanNode left) {
        this.left = left;
    }

    public void setRight(HuffmanNode right) {
        this.right = right;
    }
}
