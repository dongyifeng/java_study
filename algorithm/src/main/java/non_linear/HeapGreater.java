package non_linear;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/*
T 一定是非基础类
 */
public class HeapGreater<T> {

    public class DataNode<T> {
        public T data;

        public DataNode(T data) {
            this.data = data;
        }
    }

    private ArrayList<T> heap;
    // 存储数据在 ArrayList 中位置信息
    // [a , b , c]
    // a -> 0
    // b -> 1
    // c -> 2
    private HashMap<DataNode<T>, Integer> indexMap;
    private int size;
    private Comparator<? super T> comparator;


    public HeapGreater(Comparator<T> comparator) {
        heap = new ArrayList<T>();
        indexMap = new HashMap<DataNode<T>, Integer>();
        size = 1;
        this.comparator = comparator;
    }

    public void build(ArrayList<T> data) {
        heap = data;
        size = data.size();
        heap.set(0, null);
        indexMap.clear();
        for (int i = 1; i < size; i++) {
            indexMap.put(new DataNode<T>(heap.get(i)), i);
        }

        int n = data.size() >> 1;
        for (int i = n; i >= 0; i--) {
            heapInsert(i);
        }
    }

    public boolean contains(T obj) {
        return indexMap.containsKey(obj);
    }

    public T peek() {
        if (size > 1) {
            return heap.get(1);
        }
        return null;
    }

    public T pop() {
        T res = peek();
        if (res == null) {
            return null;
        }
        swap(1, size);
        indexMap.remove(res);
        heap.remove(size--);
        heapify(1);
        return res;
    }

    public void push(T obj) {
        if (obj == null) {
            return;
        }
        size += 1;
        heap.add(obj);
        indexMap.put(new DataNode<T>(obj), size);
        heapInsert(size);
    }

    // 将堆中最小元素弹出，并将元素 x 入堆
    public T replace(T obj) {
        T res = peek();
        if (res == null) {
            return obj;
        }
        if (obj == null) {
            return pop();
        }

        indexMap.remove(res);
        indexMap.put(new DataNode<T>(obj), 1);
        heap.set(1, obj);
        heapify(1);
        return res;
    }

    // 先将 obj 入队，然后 pop
    public T pushAndPop(T obj) {
        T res = peek();
        if (res == null) {
            return obj;
        }
        if (obj == null) {
            return pop();
        }
        if (comparator.compare(obj, res) < 0) {
            return obj;
        }

        indexMap.remove(res);
        indexMap.put(new DataNode<T>(obj), 1);
        heap.set(1, obj);
        heapify(1);
        return res;
    }


    public int left(int index) {
        return 2 * index;
    }

    public int right(int index) {
        return 2 * index + 1;
    }

    public int parent(int index) {
        return index >> 1;
    }

    public boolean isEmpty(int index) {
        return size <= 1;
    }

    public void remove(T obj) {
        T replace = heap.get(size);
        int index = indexMap.get(replace);
        indexMap.remove(replace);
        heap.remove(size--);
        // 删除不是最后一个节点
        if (obj != replace) {
            heap.set(index, replace);
            indexMap.put(new DataNode<T>(replace), index);
            resign(replace);
        }
    }

    public void heapify(int index) {
        int left = left(index);
        int right = right(index);

        while (left < size) {
            // 从 left 和 right 选择较大的下标给 largest
            int largest = right <= size && comparator.compare(heap.get(right), heap.get(left)) > 0 ? right : left;

            // 较大孩子与父节点比较
            largest = comparator.compare(heap.get(largest), heap.get(index)) > 0 ? largest : index;

            // 父节点比 largest 大，不需要继续下沉了
            if (largest == index) {
                break;
            }

            swap(largest, index);
            index = largest;
            left = left(index);
        }
    }

    public void heapInsert(int index) {
        int parent = parent(index);
        while (parent > 0 && comparator.compare(heap.get(index), heap.get(parent)) > 0) {
            swap(index, parent);
            index = parent;
            parent = parent(index);
        }
    }

    public void resign(T obj) {
        int index = indexMap.get(obj);
        heapInsert(index);
        heapify(index);
    }

    public List<T> getAllElements() {
        List<T> res = new ArrayList<T>(size);
        for (int i = 1; i < size + 1; i++) {
            res.add(heap.get(i));
        }
        return res;
    }

    // 同时维护 indexMap 和 heap
    public void swap(int i, int j) {
        T o1 = heap.get(i);
        T o2 = heap.get(j);
        heap.set(i, o2);
        heap.set(j, o1);
        indexMap.put(new DataNode<T>(o2), i);
        indexMap.put(new DataNode<T>(o1), j);
    }

    public static void main(String[] args) {
        int i =1;
        System.out.println(i--);
        System.out.println(i);

    }
}
