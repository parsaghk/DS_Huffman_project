package ir.ac.kntu;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) {

        HashMap<Character, Integer> map = new HashMap<>();

        map = analyzeText("./src/ir/ac/kntu/text.txt");

        System.out.println("This is map");
        map.entrySet().forEach(entry -> {
            System.out.println(entry.getValue() + " " +  entry.getKey());

        });
        System.out.println("-------");

        HuffmanNode[] nodes = convertMapToArray(map);

        HuffmanTree tree = new HuffmanTree(nodes);

        System.out.println("These are codes");
        tree.mapCodes.entrySet().forEach(entry ->{

            System.out.println(entry.getKey() + ": " + entry.getValue());
        });
        System.out.println("------");
        System.out.println("Begin write code");
        tree.compressCode();

        System.out.println("Extract code");
        tree.extractCode();

        System.out.println("bytes");
        tree.extractCode();

    }

    public static HashMap<Character, Integer> analyzeText(String file) {

        HashMap<Character, Integer> map = new HashMap<>();
        try {
            int asciiCode;
            BufferedReader reader = new BufferedReader(new FileReader(file));
            while ((asciiCode = reader.read()) != -1) {
                char character = (char) asciiCode;

                if (map.containsKey(character)) {
                    int value = map.get(character);

                    map.put(character, ++value);
                } else {
                    map.put(character, 1);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return map;
    }

    public static HuffmanNode[] convertMapToArray(HashMap<Character, Integer> map) {

        Object[] values = new Integer[map.size()];
        Object[] keys = new Character[map.size()];
        keys = map.keySet().toArray();
        values = map.values().toArray();
        HuffmanNode[] nodes = new HuffmanNode[map.size()];
        for (int i = 0; i < map.size(); i++) {
            char ch = (char) keys[i];
            int character = (int) ch;
            int value = (int) values[i];
            nodes[i] = new HuffmanNode(character, value);

        }

        return nodes;

    }

}
