package dp;

public class Kiki3 {
    //start偶数，end偶数  start<=end
    public static int minCcoins1(int add, int times, int del, int start, int end) {
        if (start > end) {
            return -1;
        }
        return process(0, start, end, add, times, del, 2 * end, ((end - start) / 2) * add);
    }

    /**
     * end 人气向 start 改变
     *
     * @param cost      之前已经花了多少钱【可变】
     * @param start     起始人气【固定】
     * @param end       目标人气 【可变】
     * @param add       点赞花费 C 币 【固定】
     * @param times     送礼花费 C 币 【固定】
     * @param del       私聊花费 C 币 【固定】
     * @param limitCoin 已经使用的币大到什么程度不需要再尝试了 【固定】
     * @return
     */
    public static int process(int cost, int start, int end, int add, int times, int del,
                              int limitAim, int limitCoin) {
        if (cost > limitCoin) {
            return Integer.MAX_VALUE;
        }
        if (end < 0) {
            return Integer.MAX_VALUE;
        }
        if (end > limitAim) {
            return Integer.MAX_VALUE;
        }
        if (start == end) {
            return cost;
        }

        int min = Integer.MAX_VALUE;
        //让人气-2的方式
        int p1 = process(cost + add, start, end - 2, add, times, del, limitAim, limitCoin);
        if (p1 != Integer.MAX_VALUE) {
            min = p1;
        }

        //让人气+2的方式
        int p2 = process(cost + del, start, end + 2, add, times, del, limitAim, limitCoin);
        if (p2 != Integer.MAX_VALUE) {
            min = Math.min(min, p2);
        }

        if ((end & 1) == 0) {
            //让人气*2的方式
            int p3 = process(cost + times, start, end / 2, add, times, del, limitAim, limitCoin);
            if (p3 != Integer.MAX_VALUE) {
                min = Math.min(min, p3);
            }
        }
        return min;
    }

    public static void main(String[] args) {
        int add = 6;
        int times = 5;
        int del = 1;
        int start = 10;
        int end = 30;
        System.out.println(minCcoins1(add, times, del, start, end));
    }
}
