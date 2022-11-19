package prefixTree;

import java.util.TreeMap;

public class PrefixTree {
    public class Node {
        public TreeMap<String, Node> children = new TreeMap<>();

    }

    private Node root = new Node();

    public void add(String[] data) {
        if (data == null || data.length == 0) {
            return;
        }
        Node node = root;
        for (String item : data) {
            if (!node.children.containsKey(item)) {
                node.children.put(item, new Node());
            }
            node = node.children.get(item);
        }
    }

    public void print(Node node, int level) {
        node.children.forEach((k, v) -> {
            System.out.println(getSpace(level) + k);
            print(v, level + 1);
        });
    }

    public static void print(String[] arr) {
        if (arr == null || arr.length == 0) {
            return;
        }
        PrefixTree prefixTree = new PrefixTree();
        for (int i = 0; i < arr.length; i++) {
            prefixTree.add(arr[i].split("\\\\"));
        }
        prefixTree.print(prefixTree.root, 0);
    }

    public String getSpace(int level) {
        StringBuffer res = new StringBuffer(10 * level);
        for (int i = 0; i < level; i++) {
            res.append("        ");
        }
        return res.toString();
    }


    public static void main(String[] args) {
        String[] arr=new String[]{ "b\\cst","d\\","a\\d\\e","a\\b\\c" };
        PrefixTree.print(arr);
    }
}
