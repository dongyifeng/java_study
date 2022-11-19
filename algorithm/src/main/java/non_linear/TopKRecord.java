package non_linear;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TopKRecord {
    public class Node {
        public String str;
        public int times;

        public Node(String str, int times) {
            this.str = str;
            this.times = times;
        }
    }


    private HashMap<String, Node> strNodeMap;
    private Node[] heap;
    private int size;

    private HashMap<Node, Integer> nodeIndexMap;

    public TopKRecord(int size) {
        heap = new Node[size];
        size = 0;
        strNodeMap = new HashMap<String, Node>();
        nodeIndexMap = new HashMap<Node, Integer>();
    }

    public void add(String str) {
        Node curNode = null;
        int preIndex = -1;
        // str 第一次出现
        if (!strNodeMap.containsKey(str)) {
            curNode = new Node(str, 1);
            strNodeMap.put(str, curNode);
            nodeIndexMap.put(curNode, -1);
        } else {   // 并非第一次出现
            curNode = strNodeMap.get(str);
            curNode.times++;
            preIndex = nodeIndexMap.get(curNode);
        }

        // 新增
        if (preIndex == -1) {
            // 堆满了
            if (size == heap.length) {
                if (heap[0].times < curNode.times) {
                    nodeIndexMap.put(heap[0], -1);
                    nodeIndexMap.put(curNode, 0);
                    heap[0] = curNode;
                    heapify(0);
                }
            } else {
                nodeIndexMap.put(curNode, size);
                heap[size] = curNode;
                heapInsert(size++);
            }
        } else {
            // （小顶堆）堆中数据变大，节点可能下沉，所以向下 heapify
            heapify(preIndex);
        }
    }

    private void heapify(int index) {
        int l = left(index);
        int r = l + 1;

        int smallest = index;
        while (l < size) {
            if (l < size && heap[l].times < heap[index].times) {
                smallest = l;
            }
            if (r < size && heap[r].times < heap[index].times) {
                smallest = r;
            }
            if (smallest == index) {
                break;
            }
            swap(index, smallest);

            index = smallest;
            l = left(index);
            r = l + 1;
        }
    }

    private void heapInsert(int index) {
        while (index != 0) {
            int parent = parent(index);
            if (heap[index].times < heap[parent].times) {
                swap(index, parent);
                index = parent;
            } else {
                break;
            }
        }
    }

    public void printTopK() {
        System.out.println("Top:");
        for (int i = 0; i < heap.length; i++) {
            if (heap[i] == null) {
                break;
            }
            System.out.println(String.format("Str:%s,Times:%s", heap[i].str, heap[i].times));
        }
    }

    private void swap(int index1, int index2) {
        Node node1 = heap[index1];
        Node node2 = heap[index2];
        heap[index1] = node2;
        heap[index2] = node1;

        nodeIndexMap.put(node1, index2);
        nodeIndexMap.put(node2, index1);
    }

    private int parent(int index) {
        return (index - 1) / 2;
    }

    private int left(int index) {
        return index * 2 + 1;
    }

    private int right(int index) {
        return index * 2 + 2;
    }


    public static void main(String[] args) {
        List<String> list = new ArrayList<String>();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");
        list.add("a");
        list.add("c");
        list.add("d");
        list.add("a");
        list.add("c");
        list.add("d");
        list.add("b");
        list.add("b");
        list.add("b");
        list.add("b");

        list.add("a");

        TopKRecord topKRecord = new TopKRecord(3);
        for (String item : list) {
            topKRecord.add(item);
        }
        topKRecord.printTopK();
    }
}
