package other;


import java.util.*;

public class LFU1 {
    public static class Node implements Comparable<Node> {
        public int key;
        public int value;
        // 这个节点发生get或者set的次数总和
        public int count;
        // 最后一次操作的时间
        public int time;

        public Node(int key, int value, int count, int time) {
            this.key = key;
            this.value = value;
            this.count = count;
            this.time = time;
        }

        // 缓存淘汰优先级
        // 最少使用（count 越小越容易被淘汰）
        // count 相同，时间越早越容易被淘汰（time 越小越容易被淘汰）
        @Override
        public int compareTo(Node o) {
            return count == o.count ? Integer.compare(time, o.time) : Integer.compare(count, o.count);
        }

        @Override
        public String toString() {
            return "Node{" + "key=" + key + ", value=" + value + ", count=" + count + ", time=" + time + '}';
        }
    }

    public static class LFUCache {
        // 缓存过期优先级
        TreeSet<Node> set = new TreeSet<>();
        Map<Integer, Node> map = new HashMap<>();
        int capacity;
        int time = 0; // 用来计算缓存时间

        public LFUCache(int capacity) {
            this.capacity = Math.max(capacity, 0);
        }

        public Integer get(int key) {
            if (!map.containsKey(key)) {
                return null;
            }
            Node node = map.get(key);
            set(key, node.value);
            return node.value;
        }

        public void set(int key, int value) {
            this.time += 1;
            // 更新
            if (map.containsKey(key)) {
                Node node = map.get(key);
                // 删除再插入（node 的count 和 time 变化了，TreeSet 认为是不同的数据）
                set.remove(node);
                node.time = this.time;
                node.count++;
                node.value = value;
                set.add(node);
                map.put(key, node);
                return;
            }

            // 新增
            // 如果内存慢了，淘汰一条旧数据
            if (this.capacity == this.map.size()) {
                remove();
            }
            Node node = new Node(key, value, 1, this.time);
            map.put(key, node);
            set.add(node);
        }

        public void remove() {
            if (map.size() == 0) {
                return;
            }
            Node node = set.first();
            map.remove(node.key);
            set.remove(node);
        }


        public static boolean check(LFU1.LFUCache lfu1, LFU2.LFUCache lfu2, LFU3.LFUCache lfu3) {
            if (lfu1.map.size() != lfu2.heads.size() || lfu1.map.size() != lfu3.map.size()) {
                return false;
            }

            for (int key : lfu1.map.keySet()) {
                if (!lfu2.map.containsKey(key) || !lfu3.map.containsKey(key)) {
                    return false;
                }
                Node node = lfu1.map.get(key);
                LFU2.Node node2 = lfu2.map.get(key);
                LFU2.Node node3 = lfu2.map.get(key);

                if (node.value != node2.value || node.count != node2.count || node.value != node3.value || node.count != node3.count) {
                    return false;
                }
            }
            return true;

        }

        public static void check() {
            LFU1.LFUCache lfu1 = new LFU1.LFUCache(3);
            LFU2.LFUCache lfu2 = new LFU2.LFUCache(3);
            LFU3.LFUCache lfu3 = new LFU3.LFUCache(3);

            for (int i = 0; i < 100000; i++) {
                int command = (int) (Math.random() * 3) % 2;
                int key = (int) (Math.random() * 10);
                int value = (int) (Math.random() * 10);

                if (command == 0) {
                    lfu1.set(key, value);
                    lfu2.set(key, value);
                    lfu3.set(key, value);
                } else {
                    lfu1.get(key);
                    lfu2.get(key);
                    lfu3.get(key);
                }
                if (!check(lfu1, lfu2, lfu3)) {
                    System.out.println("ERROR:res1:" + key);
                }
            }

            System.out.println("Nice");
        }

        public static void main(String[] args) {
            check();
        }
    }
}
