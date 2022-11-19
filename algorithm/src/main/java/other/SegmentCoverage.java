package other;

import java.util.*;
import java.util.stream.Collectors;

public class SegmentCoverage {
    /*
    一条直线上有 n 个线段，第 i 个线段的坐标为(x1[i],x2[i])。请你计算出直线上重叠线段数量最多的地方，有多少个线段相互重叠？
     */
    public static int segmentCoverMax(int[][] matrix) {
        if (matrix == null || matrix.length == 0) {
            return 0;
        }
        // 排序
        Arrays.sort(matrix, (e1, e2) -> (e1[0] - e2[0]));

        int res = 0;
        // map 中 value 的和
        int sum = 0;

        // key：是 arr 的 end；value：end 的出现次数
        TreeMap<Integer, Integer> map = new TreeMap<>();
        for (int i = 0; i < matrix.length; i++) {
            int start = matrix[i][0];
            int end = matrix[i][1];

            int count = map.getOrDefault(end, 0) + 1;
            map.put(end, count);
            // map 中 value 的和增加了 1
            sum += 1;

            // 将 map 中所有 key( end ) 小于等于 start 的删除掉
            while (true) {
                Map.Entry<Integer, Integer> entry = map.floorEntry(start);
                if (entry == null) {
                    break;
                }
                map.remove(entry.getKey());

                // map 中 value 的和减少 entry.getValue()
                sum -= entry.getValue();
                start = entry.getKey();
            }

            res = Math.max(res, sum);
        }

        return res;
    }

    /*
    平面内有 n 个矩形，第 i 个矩形的左下角坐标为$(x_1[i],y_1[i])$，右上角坐标为：(x2[i],y2[i])。
    如果两个或多个矩形有公共区域，则认为他们是相互重叠的（不考虑边界和角落）。请你计算出平面内重叠矩形数量最多的地方，有多少个矩形相互重叠？
     */
    public static int rectangleCoverMax(int[][] matrix) {
        if (matrix == null || matrix.length == 0) {
            return 0;
        }
        // 按照底边排序
        Arrays.sort(matrix, (e1, e2) -> (e1[1] - e2[1]));

        int res = 0;
        // map 中 value 的和
        int count = 0;

        // key：是 arr 的 end；value：矩阵集合
        TreeMap<Integer, List<int[]>> map = new TreeMap<>();
        for (int i = 0; i < matrix.length; i++) {
            int x1 = matrix[i][0];
            int y1 = matrix[i][1];
            int x2 = matrix[i][2];
            int y2 = matrix[i][3];

            List<int[]> list = map.getOrDefault(y2, new ArrayList<>());
            list.add(new int[]{x1, x2});
            map.put(y2, list);
            count += 1;

            // 将 map 中所有 key( end ) 小于等于 start 的删除掉
            while (true) {
                Map.Entry<Integer, List<int[]>> entry = map.floorEntry(y1);
                if (entry == null) {
                    break;
                }
                count -= entry.getValue().size();
                map.remove(entry.getKey());

                // map 中 value 的和减少 entry.getValue()
                y1 = entry.getKey();
            }

            int[][] segment_matrix = new int[count][2];
            int j = 0;
            for (int key : map.keySet()) {
                for (int[] rec : map.get(key)) {
                    segment_matrix[j][0] = rec[0];
                    segment_matrix[j][1] = rec[1];
                }
                j += 1;
            }
            res = Math.max(res, segmentCoverMax(segment_matrix));
        }

        return res;
    }


    public static int[][] generator_ramdon_arr() {
        int size = (int) (Math.random() * 5) + 1;
        int[][] arr = new int[size][2];


        for (int i = 0; i < size; i++) {
            arr[i] = new int[2];
            arr[i][0] = (int) (Math.random() * 10) + 1;
            arr[i][1] = arr[i][0] + (int) (Math.random() * 10) + 1;
        }
        return arr;
    }

    public static int[][] generator_ramdon_matrix() {
        int size = (int) (Math.random() * 5) + 1;
        int[][] arr = new int[size][4];


        for (int i = 0; i < size; i++) {
            arr[i] = new int[4];
            // x1
            arr[i][0] = (int) (Math.random() * 5) + 1;
            // y1
            arr[i][1] = (int) (Math.random() * 5) + 1;
            // x2
            arr[i][2] = arr[i][0] + (int) (Math.random() * 5) + 1;
            // y2
            arr[i][3] = arr[i][1] + (int) (Math.random() * 5) + 1;
        }
        return arr;
    }

    public static void check() {

        for (int i = 0; i < 1000; i++) {
            int[][] arr = generator_ramdon_matrix();
            int res = rectangleCoverMax(arr);
            System.out.println(String.format("Info res=%s,arr=%s",
                    res, String.join(",", Arrays.stream(arr).map(x -> "(" + x[0] + "," + x[1] + "," + x[2] + "," + x[3] + ")").collect(Collectors.toList()))));
//


//            if (res != res1) {
//                System.out.println(String.format("ERROR res=%s,res1=%s,arr=%s",
//                        res, res1, String.join(",", Arrays.stream(arr).map(x -> "(" + x[0] + "," + x[1] + ")").collect(Collectors.toList()))));
//            }
        }
        System.out.println("Nick");
    }

    public static void main(String[] args) {
        int[][] arr = new int[2][4];
        arr[0] = new int[]{1, 0, 3, 2};
        arr[1] = new int[]{2, 0, 3, 1};

//
        System.out.println(rectangleCoverMax(arr));

//        System.out.println(maxLevelCount(arr));
        check();
    }
}


