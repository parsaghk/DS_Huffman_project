package ir.ac.kntu;

import com.sun.org.apache.xpath.internal.objects.XObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) {

        HashMap<Character, Integer> map = new HashMap<>();

        map = analyzeText("./src/ir/ac/kntu/text.txt");

        map.entrySet().forEach(entry -> {
            System.out.println(entry.getValue() + " " + (int) entry.getKey());

        });
        convertMapToArray(map);

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

    public static void convertMapToArray(HashMap<Character, Integer> map) {

        Object[] values = new Integer[map.size()];
        Object[] keys = new Character[map.size()];
        keys = map.keySet().toArray();
        values = map.values().toArray();
//        for (int i = 0; i < map.size(); i++) {
//            System.out.print((Character) keys[i] + " ");
//
//        }
//        System.out.println(map.size());

        //  System.out.println(keys.toString());
        //  System.out.println(values.toString());


    }

}
