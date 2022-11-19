package string_demo;


import java.util.HashMap;
import java.util.Map;

public class Remove {

    public static String remove(String str) {
        if (str == null || str.trim().length() < 2) {
            return str;
        }

        Map<Character, Integer> map = new HashMap<>();
//        int[] map = new int[256];
        for (int i = 0; i < str.length(); i++) {
            map.put(str.charAt(i), map.getOrDefault(str.charAt(i), 0) + 1);
        }

        int minACSIndex = 0;
        for (int i = 0; i < str.length(); i++) {
            map.put(str.charAt(i), map.get(str.charAt(i)) - 1);
            minACSIndex = str.charAt(minACSIndex) > str.charAt(i) ? i : minACSIndex;
            if (map.get(str.charAt(i)) == 0) {
                break;
            }

        }

        return String.valueOf(str.charAt(minACSIndex)) + remove(str
                .substring(minACSIndex + 1)
                .replaceAll(String.valueOf(str.charAt(minACSIndex)), ""));
    }

    public static String remove2(String str) {
        if (str == null || str.trim().length() < 2) {
            return str;
        }

        int[] map = new int[256];
        for (int i = 0; i < str.length(); i++) {
            map[str.charAt(i)]++;
        }

        int minACSIndex = 0;
        for (int i = 0; i < str.length(); i++) {

            map[str.charAt(i)]--;
            minACSIndex = str.charAt(minACSIndex) > str.charAt(i) ? i : minACSIndex;
            if (map[str.charAt(i)] == 0) {
                break;
            }
        }

        return String.valueOf(str.charAt(minACSIndex)) + remove2(str
                .substring(minACSIndex + 1)
                .replaceAll(String.valueOf(str.charAt(minACSIndex)), ""));
    }

    public static void main(String[] args) {
        String str = "taabcbabct";
        System.out.println(remove(str));
        System.out.println(remove2(str));
//
        str = "acbc";
        System.out.println(remove(str));

        str = "acbc";
        System.out.println(remove2(str));

        str = "dbcacbca";
        System.out.println(remove(str));
        System.out.println(remove2(str));
    }
}
