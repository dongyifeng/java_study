package dongyf.study.java.entry;

import java.util.*;
import java.util.stream.Collectors;

public class Demo {
    public static Map<String, Integer> getMap2() {
        try {
            String[] mapFeatures = new String[]{"SH601995^0.67260",
                    "03908^0.67260",
                    "SZ300059^0.67260",
                    "BK0065^0.67260"};
            if (mapFeatures == null) {
                return null;
            }
            List<String> list = Arrays.stream(mapFeatures)
                    .map(item -> item.split("\\^"))
                    .sorted(Comparator.comparing(item -> -Double.parseDouble(item[1])))
                    .limit(5)
                    .map(arr -> arr[0])
                    .collect(Collectors.toList());

            Map<String, Integer> result = new HashMap<>(list.size());
            for (int i = 0; i < list.size(); i++) {
                result.put(list.get(i), i);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String[] getList() {
        try {
            List<String> features = new ArrayList();
            features.add("SH601995");
            return features.stream().limit(3).toArray(String[]::new);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String[] calculate() {
        try {
            String[] result = getDefaultResult();
            Map<String, Integer> map = getMap2();
            if (map == null || map.isEmpty()) {
                return result;
            }

            String[] list = getList();
            if (list == null) {
                return result;
            }

            for (int i = 0; i < list.length; i++) {
                String item = list[i];
                if (map.containsKey(item)) {
                    result[i] = String.valueOf(map.get(item));
                }
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public  static String defaultValue() {
        return "-1";
    }

    public static String[] getDefaultResult() {
        String[] result = new String[3];
        for (int i = 0; i < result.length; i++) {
            result[i] = defaultValue();
        }
        return result;
    }

    public static void main(String[] args) {
//        System.out.println(getMap2());
        System.out.println(String.join(",",calculate()));
    }
}
