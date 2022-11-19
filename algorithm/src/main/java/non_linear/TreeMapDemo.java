package non_linear;

import java.util.TreeMap;

public class TreeMapDemo {

    public static void main(String[] args) {
        TreeMap<Integer, String> treeMap = new TreeMap<Integer, String>();

        treeMap.put(7, "我是7");
        treeMap.put(5, "我是5");
        treeMap.put(4, "我是4");
        treeMap.put(3, "我是3");
        treeMap.put(9, "我是9");
        treeMap.put(2, "我是2");

        System.out.println(treeMap.containsKey(5));
        System.out.println(treeMap.get(5));
        System.out.println(treeMap.firstKey() + ",我最小");
        System.out.println(treeMap.lastKey() + ",我最大");
        System.out.println(treeMap.floorKey(8) + ",在表中所有 <= 8 的数中，我离 8 最近");
        System.out.println(treeMap.ceilingKey(8) + ",在表中所有 >= 8 的数中，我离 8 最近");
        System.out.println(treeMap.floorKey(7) + ",在表中所有 <= 7 的数中，我离 7 最近");
        System.out.println(treeMap.ceilingKey(7) + ",在表中所有 >= 7 的数中，我离 7 最近");


        treeMap.remove(5);
        System.out.println(treeMap.get(5) + ",删除就没有了哦");
    }


}
