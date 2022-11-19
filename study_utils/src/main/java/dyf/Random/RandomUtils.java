package dyf.Random;

import com.google.common.collect.Lists;

import java.util.*;

public class RandomUtils<T> {

    private static Random random = new Random();

    public static <T> List<T> samples(List<T> list, int k) {
        if (list == null || list.isEmpty() || list.size() <= k) {
            return list;
        }
        List<Integer> index = randomRange(list.size() - 1, k);

        List<T> res = new ArrayList<>(index.size());
        for (int i : index) {


            if (i >= list.size()) {
                System.out.println("ERROR" + index + "," + list);
            }

            res.add(list.get(i));
        }
        return res;
    }

    public static List<Integer> randomRange(int maxSize, int count) {
        int[] status = new int[maxSize + 1];

        Random random = new Random();
        List<Integer> res = new ArrayList<Integer>(count);
        for (int i = 0; i < count; i++) {
            int num = random.nextInt(maxSize);
            if (status[num] == 0) {
                res.add(num);
                status[num] = num == maxSize ? 1 : num + 1;
            } else {
                int index = num;
                while (status[index] != 0) {
                    index = status[index];
                }
                res.add(index);
                status[index] = index == maxSize ? 1 : index + 1;
            }
        }
        return res;
    }


    public static List<Integer> sampleFloyd(List<Integer> list, int count, int start, int end) {
        if (count == 0) {
            return list;
        }

        return null;
    }

    public static Set<Integer> floyd2(int m, int n) {
        Set<Integer> set = new HashSet<>();
        if (m > n) {
            return set;
        }

        for (int i = n - m + 1; i < n + 1; i++) {
            int num = nextInt(1, i);
            if (set.contains(num)) {
                set.add(n);
            } else {
                set.add(num);
            }
        }

        return set;
    }

    public static void floyd1(int m, int n, Set<Integer> set) {
        if (m <= 0) {
            return;
        }
        floyd1(m - 1, n - 1, set);
        int num = nextInt(m, n);
        if (set.contains(num)) {
            set.add(n);
        } else {
            set.add(num);
        }
    }

    private static List<Integer> getRandomIndex2(int maxSize, int count) {
        if (maxSize < count) {
            return Lists.newArrayList();
        }
        Random random = new Random();
        Set<Integer> indexSet = new HashSet<Integer>();
        for (int i = 0; i < count; i++) {
            int index = random.nextInt(maxSize);
            if (!indexSet.add(index)) {
                i--;
            }
        }
        return new ArrayList<Integer>(indexSet);
    }

    public static int nextInt(int origin, int bound) {
        return random.nextInt(bound - origin) + origin;
    }

    private static List<Integer> getRandomIndex3(int maxSize, int count) {
        if (maxSize < count) {
            return Lists.newArrayList();
        }

        List<Integer> tmp = new ArrayList<>(maxSize);

        for (int i = 0; i < maxSize; i++) {
            tmp.add(i);
        }
        Collections.shuffle(tmp);
        return tmp.subList(0, count);
    }

    public static List<String> generatorRandomList(int maxSize) {
        int n = random.nextInt(maxSize) + 1;
        List<String> res = new ArrayList<>(n);


        char[] array = new char[]{'A', 'a', 'B', 'b', 'C', 'c', 'D', 'd', 'E', 'e', 'F', 'f', 'G', 'g', 'H', 'h', 'I', 'i', 'J', 'j', 'K', 'k', 'L', 'l',
                'M', 'm', 'N', 'n', 'O', 'o', 'P', 'p', 'Q', 'q', 'R', 'r', 'S', 's', 'T', 't', 'U', 'u', 'V', 'v', 'W', 'w', 'X', 'x', 'Y', 'y', 'Z', 'z'};

        int len = array.length;
        for (int i = 0; i < n; i++) {
            res.add(String.valueOf(array[random.nextInt(len)]));
        }
        return res;
    }


    public static void main(String[] args) {
//        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
//        for (int i = 0; i < 10000000; i++) {
//            List<Integer> integers = randomRange(100, 10);
//            for (int num : randomRange(100, 10)) {
//                map.put(num, map.getOrDefault(num, 0) + 1);
//            }
//            Set<Integer> set = new HashSet<Integer>(integers);
//            if (set.size() != integers.size()) {
//                System.out.println("ERROR" + set + integers);
//            }
//        }
//        System.out.println(map);


//        long time = System.currentTimeMillis();
//        for (int i = 0; i < 1000000; i++) {
//            randomRange(100, 10);
//        }
//
//        System.out.println(System.currentTimeMillis() - time);
//
//
//        time = System.currentTimeMillis();
//        for (int i = 0; i < 1000000; i++) {
//            getRandomIndex2(100, 10);
//        }
//        System.out.println(System.currentTimeMillis() - time);
//
//
//        time = System.currentTimeMillis();
//        for (int i = 0; i < 1000000; i++) {
//            getRandomIndex3(100, 10);
//        }
//        System.out.println(System.currentTimeMillis() - time);


//
//        Random random=new Random();
//        random.ints(90, 100).limit(10).forEach(x-> System.out.println(x));
//
//
//        random.nex


//        Map<Integer, Integer> map = new HashMap<>();
//
//        for (int i = 0; i < 100000; i++) {
////            int num = nextInt(10, 20);
//            Set<Integer> set = floyd2(5, 100);
//            for (int num : set) {
//                map.put(num, map.getOrDefault(num, 0) + 1);
//            }
//
//        }
//        System.out.println(map);
//
//
//        Set<Integer> set = new HashSet<>();
//        floyd2(5, 100);
//        System.out.println(set);

        Map<String, Integer> map = new HashMap<>();
        for (int i = 0; i < 1000000; i++) {


            for (String key : generatorRandomList(100)) {
                map.put(key, map.getOrDefault(key, 0) + 1);
            }


//            System.out.println(samples(generatorRandomList(100), 10));
        }
        System.out.println(map);


    }
}
