package other;


import java.util.*;

public class LFU3 {
    public static class Node {
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

        @Override
        public String toString() {
            return "Node{" + "key=" + key + ", value=" + value + ", count=" + count + ", time=" + time + '}';
        }
    }


    public static class NodeComparator implements Comparator<Node> {

        // 缓存淘汰优先级
        // 最少使用（count 越小越容易被淘汰）
        // count 相同，时间越早越容易被淘汰（time 越小越容易被淘汰）
        @Override
        public int compare(Node o1, Node o2) {
            return o1.count == o2.count ? Integer.compare(o1.time, o2.time) : Integer.compare(o1.count, o2.count);
        }
    }

    public static class LFUCache {
        // 缓存过期优先级
        HeapGreater<Node> heap = new HeapGreater<>(new NodeComparator());


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
                node.time = this.time;
                node.count++;
                node.value = value;
                heap.heapify(node);
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
            heap.push(node);
        }

        public void remove() {
            if (map.size() == 0) {
                return;
            }
            Node node = heap.pop();
            map.remove(node.key);
        }
    }
}
