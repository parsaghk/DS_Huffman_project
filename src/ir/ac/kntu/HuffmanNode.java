package ir.ac.kntu;

public class HuffmanNode {

    char character;
    int data;

    HuffmanNode left;
    HuffmanNode right;

    public HuffmanNode(char character, int data) {
        this.character = character;
        this.data = data;
    }

    public HuffmanNode(int data) {
        this.data = data;
        this.character = ' ';
    }
}
