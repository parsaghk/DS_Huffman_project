package ir.ac.kntu;

public class HuffmanNode {

    int character;
    int data;

    HuffmanNode left;
    HuffmanNode right;

    public HuffmanNode(int character, int data) {
        this.character = character;
        this.data = data;
    }

    public HuffmanNode(int data) {
        this.data = data;
        this.character = ' ';
    }
}
