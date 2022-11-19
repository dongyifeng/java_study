package sorted_table;

import java.util.*;

/**
 * 给定一个 N * 3 的矩阵 matrix ，对于每个长度为 3 的小数组 arr，都表示一个大楼的三个数据。arr[0] 表示大楼的左边界，
 * arr[1] 表示大楼的右边界，arr[2] 表示大楼的高度（一定大于 0）。每座大楼的地基都在 X 轴上，大楼之间可能会有重叠，请返回整体的轮廓线数组。
 */
public class BuildingOutline {

    // 描述高度变化的对象
    public static class Node {
        // x 轴上的值
        public int x;
        // true 为加入，false 为删除
        public boolean isAdd;
        // 高度
        public int height;

        public Node(int x, boolean isAdd, int height) {
            this.x = x;
            this.isAdd = isAdd;
            this.height = height;
        }
    }

    // 排序策略
    // 第一个维度：x 值从小到大
    // 第二个维度："加入" 排在前，"删除"排在后：插入后才能删除。
    // 第三个维度：前两个维度相同，则认为两个对象相等，谁在前都行
    public static class NodeComparator implements Comparator<Node> {
        @Override
        public int compare(Node o1, Node o2) {
            if (o1.x != o2.x) {
                return o1.x - o2.x;
            }
            if (o1.isAdd != o2.isAdd) {
                return o1.isAdd ? -1 : 1;
            }
            return 0;
        }
    }

    public static List<List<Integer>> buildingOutline(int[][] matrix) {
        List<Node> nodes = new ArrayList<>();
        // 每个大楼的轮廓数据，产生两个描述高度变化的对象
        for (int i = 0; i < matrix.length; i++) {
            int[] row = matrix[i];
            nodes.add(new Node(row[0], true, row[2]));
            nodes.add(new Node(row[1], false, row[2]));
        }
        // 按照规定的排序规则排序
        nodes.sort(new NodeComparator());

        // 有序表
        TreeMap<Integer, Integer> mapHeightTimes = new TreeMap<>();
        // 每个位置对应的最大高度
        Map<Integer, Integer> mapXHeight = new HashMap<>();
        for (int i = 0; i < nodes.size(); i++) {
            Node node = nodes.get(i);
            // 插入操作
            if (node.isAdd) {
                mapHeightTimes.put(node.height, mapHeightTimes.getOrDefault(node.height, 0) + 1);
            } else {
                // 删除操作
                if (mapHeightTimes.get(node.height) == 1) {
                    mapHeightTimes.remove(node.height);
                } else {
                    mapHeightTimes.put(node.height, mapHeightTimes.get(node.height) - 1);
                }
            }

            // 根据 mapHeightTimes 中的最大值，设置 mapXHeight
            if (mapHeightTimes.isEmpty()) {
                mapXHeight.put(node.x, 0);
            } else {
                // 通过 mapHeightTimes.lastKey() 获取当前的最大高度
                mapXHeight.put(node.x, mapHeightTimes.lastKey());
            }
        }

        // 每一个 List<Integer> 表示一个轮廓线，【开始位置，结束位置，高度】
        List<List<Integer>> res = new ArrayList<>();
        // 一个轮廓线的开始位置
        int start = 0;
        // 之前的最大高度
        int preHeight = 0;

        // 根据 mapXHeight 生成 res 数组
        for (int curX : mapXHeight.keySet()) {
            // 当前位置的最大高度
            int curMaxHeight = mapXHeight.get(curX);

            // 当前位置的最大高度与之前最大高度不一致时
            if (preHeight != curMaxHeight) {
                if (preHeight != 0) {
                    res.add(new ArrayList<>(Arrays.asList(start, curX, preHeight)));
                }
                start = curX;
                preHeight = curMaxHeight;
            }
        }

        return res;
    }

    public static List<List<Integer>> buildingOutline2(int[][] matrix) {
        List<Node> nodes = new ArrayList<>();
        // 每个大楼的轮廓数据，产生两个描述高度变化的对象
        for (int i = 0; i < matrix.length; i++) {
            int[] row = matrix[i];
            nodes.add(new Node(row[0], true, row[2]));
            nodes.add(new Node(row[1], false, row[2]));
        }
        // 按照规定的排序规则排序
        nodes.sort(new NodeComparator());

        // 有序表
        TreeMap<Integer, Integer> mapHeightTimes = new TreeMap<>();
//        // 每个位置对应的最大高度
//        Map<Integer, Integer> mapXHeight = new HashMap<>();
        // 每一个 List<Integer> 表示一个轮廓线，【开始位置，结束位置，高度】
        List<List<Integer>> res = new ArrayList<>();
        // 一个轮廓线的开始位置
        int start = 0;
        // 之前的最大高度
        int preHeight = 0;

        for (int i = 0; i < nodes.size(); i++) {
            Node node = nodes.get(i);
            // 插入操作
            if (node.isAdd) {
                mapHeightTimes.put(node.height, mapHeightTimes.getOrDefault(node.height, 0) + 1);
            } else {
                // 删除操作
                if (mapHeightTimes.get(node.height) == 1) {
                    mapHeightTimes.remove(node.height);
                } else {
                    mapHeightTimes.put(node.height, mapHeightTimes.get(node.height) - 1);
                }
            }

            // 当前位置的最大高度
            int curMaxHeight = 0;
            if (!mapHeightTimes.isEmpty()) {
                curMaxHeight = mapHeightTimes.lastKey();
            }

            // 当前位置的最大高度与之前最大高度不一致时
            if (preHeight != curMaxHeight) {
                if (preHeight != 0) {
                    res.add(new ArrayList<>(Arrays.asList(start, node.x, preHeight)));
                }
                start = node.x;
                preHeight = curMaxHeight;
            }
        }
        return res;
    }

    public static void main(String[] args) {
        int[][] matrix = new int[5][3];
        matrix[0] = new int[]{1, 14, 4};
        matrix[1] = new int[]{2, 5, 8};
        matrix[2] = new int[]{3, 6, 3};
        matrix[3] = new int[]{4, 8, 5};
        matrix[4] = new int[]{7, 11, 9};

        System.out.println(buildingOutline(matrix));
        System.out.println(buildingOutline2(matrix));
    }
}
