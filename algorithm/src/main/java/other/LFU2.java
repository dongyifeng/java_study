package other;

import java.util.*;

public class LFU2 {
    public static class Node {
        public int key;
        public int value;
        // 这个节点发生get或者set的次数总和
        public int count;
        // 双向链表上一个节点
        public Node up;
        // 双向链表下一个节点
        public Node down;

        public Node(int key, int value, int count) {
            this.key = key;
            this.value = value;
            this.count = count;
        }

        @Override
        public String toString() {
            return "Node{" + "key=" + key + ", value=" + value + ", count=" + count + '}';
        }
    }

    public static class NodeList {
        // 桶内链表的头节点
        public Node head;
        // 桶内链表的尾节点
        public Node tail;
        // 桶之间的前一个桶
        public NodeList last;
        // 桶之间的后一个桶
        public NodeList next;


        public NodeList(Node node) {
            this.head = node;
            this.tail = node;
        }

        // 把一个新的节点加入这个桶，新的节点都放在顶端变成新的头部
        public void addNodeFromHead(Node newHead) {
            newHead.down = head;
            head.up = newHead;
            head = newHead;
        }

        // 判断这个桶是不是空的
        public boolean isEmpty() {
            return this.head == null;
        }

        // 删除 node 节点并保证 node 的上下环境重新连接
        public void deleteNode(Node node) {
            if (head == tail) {
                this.head = null;
                this.tail = null;
            } else if (node == head) {
                head = node.down;
                head.up = null;
            } else if (node == tail) {
                tail = node.up;
                tail.down = null;
            } else {
                node.up.down = node.down;
                node.down.up = node.up;
            }
            node.up = null;
            node.down = null;
        }
    }


    // 总得缓存结构
    public static class LFUCache {
        // 缓存的大小限制
        public int capacity;
        // 缓存中目前有多少个节点
        public int size = 0;

        public Map<Integer, Node> map = new HashMap<>();
        // 表示节点 node在 哪个桶里
        public Map<Node, NodeList> heads = new HashMap<>();
        // 整个桶链表的头节点
        private NodeList headList;

        public LFUCache(int k) {
            this.capacity = k;
        }

        // removeNodeList：刚刚减少了一个节点的桶
        // 这个函数的功能是，判断刚刚减少了一个节点的桶是不是已经空了。
        // 1）如果不空，什么也不做
        //
        // 2)如果空了，removeNodeList 还是整个缓存结构最左的桶 (headList)。
        // 删掉这个桶的同时也要让最左的桶变成removeNodeList的下一个。
        //
        // 3)如果空了，removeNodeList不是整个缓存结构最左的桶(headList)。
        // 把这个桶删除，并保证上一个的桶和下一个桶之间还是双向链表的连接方式
        //
        // 函数的返回值表示刚刚减少了一个节点的桶是不是已经空了，空了返回true；不空返回false
        private boolean modifyHeadList(NodeList removeNodeList) {
            if (!removeNodeList.isEmpty()) {
                return false;
            }
            if (removeNodeList == headList) {
                headList = removeNodeList.next;
                if (headList != null) {
                    headList.last = null;
                }
            } else {
                removeNodeList.last.next = removeNodeList.next;
                if (removeNodeList.next != null) {
                    removeNodeList.next.last = removeNodeList.last;
                }
            }
            return true;
        }

        // node 这个节点的次数 +1 了，这个节点原来在 oldNodeList 里。
        // 把 node 从 oldNodeList 删掉，然后放到次数 +1 的桶中
        // 整个过程既要保证桶之间仍然是双向链表，也要保证节点之间仍然是双向链表
        private void move(Node node, NodeList oldNodeList) {
            // 从 oldNodeList 中删除
            oldNodeList.deleteNode(node);
            // preList表示次数 +1 的桶的前一个桶是谁
            // 如果 oldNodeList 删掉 node 之后还有节点（oldNodeList 不需要删除），oldNodeList 就是次数 +1 的桶的前一个桶
            // 如果 oldNodeList 删掉 node 之后空了，oldNodeList是需要删除的，所以次数 +1 的桶的前一个桶，是 oldNodeList 的前一个
            NodeList preList = modifyHeadList(oldNodeList) ? oldNodeList.last : oldNodeList;


            NodeList nextList = oldNodeList.next;
            // 如果 oldNodeList 没有后续了，那么肯定需要新建一个桶来盛放 node
            if (nextList == null) {
                NodeList newList = new NodeList(node);
                if (preList != null) {
                    preList.next = newList;
                }
                newList.last = preList;
                if (headList == null) {
                    headList = newList;
                }
                heads.put(node, newList);
            } else {
                // oldNodeList 有后续了，并且 oldNodeList 的后续count == node.count，直接将 node 添加到这个桶里。
                if (nextList.head.count == node.count) {
                    nextList.addNodeFromHead(node);
                    heads.put(node, nextList);
                } else {
                    // oldNodeList 的后续 count != node.count ，那么需要新建一个桶来放 node。
                    NodeList newList = new NodeList(node);
                    if (preList != null) {
                        preList.next = newList;
                    }
                    newList.last = preList;
                    newList.next = nextList;
                    nextList.last = newList;
                    if (headList == nextList) {
                        headList = newList;
                    }
                    heads.put(node, newList);
                }
            }
        }

        public void set(int key, int value) {
            // 更新
            if (map.containsKey(key)) {
                Node node = map.get(key);
                node.count++;
                node.value = value;
                move(node, heads.get(node));
            } else {
                // 新增
                // 如果缓存已满，需要淘汰一条旧数据
                if (size == capacity) {
                    // 从头部新增，从尾部删除。桶内双向链表的顺序，就是相同 count 的 time 值的排序。
                    // headList 是 count 值最小的桶，headList.tail 是 time 最小的节点。
                    Node node = headList.tail;
                    headList.deleteNode(node);
                    // 删除数据节点后，维护一下 桶，看看是否需要删除
                    modifyHeadList(headList);

                    map.remove(node.key);
                    heads.remove(node);
                    size--;
                }

                Node node = new Node(key, value, 1);
                if (headList == null) {
                    headList = new NodeList(node);
                } else {
                    // 新增节点 count = 1，如果 headList 的count 也是 1，直接将 node 加入 headList
                    if (headList.head.count == node.count) {
                        headList.addNodeFromHead(node);
                    } else {
                        // 如果如果 headList 的count 不是 1，需要新建一个 count = 1 的桶，作为 headList
                        NodeList newList = new NodeList(node);
                        newList.next = headList;
                        headList.last = newList;
                        headList = newList;
                    }
                }
                size++;
                map.put(key, node);
                heads.put(node, headList);
            }
        }

        public Integer get(int key) {
            if (!map.containsKey(key)) {
                return null;
            }

            Node node = map.get(key);
            node.count++;
            move(node, heads.get(node));
            return node.value;
        }
    }
}
