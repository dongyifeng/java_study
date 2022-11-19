package dp;

public class KiKi {

    //start偶数，end偶数  start<=end
    public static int minCcoins1(int add, int times, int del, int start, int end) {
        if (start > end) {
            return -1;
        }
        return process(0, start, end, add, times, del, ((end - start) / 2) * add);
    }

    /**
     * @param cost      之前已经花了多少钱【可变】
     * @param start     起始人气【可变】
     * @param end       目标人气 【固定】
     * @param add       点赞花费 C 币 【固定】
     * @param times     送礼花费 C 币 【固定】
     * @param del       私聊花费 C 币 【固定】
     * @param limitCoin 已经使用的币大到什么程度不需要再尝试了 【固定】
     * @return
     */
    public static int process(int cost, int start, int end, int add, int times, int del,
                              int limitCoin) {
        if (cost > limitCoin) {
            return Integer.MAX_VALUE;
        }
        if (start < 0) {
            return Integer.MAX_VALUE;
        }
        if (start > (2 * end)) {
            return Integer.MAX_VALUE;
        }
        if (start == end) {
            return cost;
        }

        int min = Integer.MAX_VALUE;
        //让人气-2的方式
        int p1 = process(cost + add, start + 2, end, add, times, del, limitCoin);
        if (p1 != Integer.MAX_VALUE) {
            min = p1;
        }
        //让人气+2的方式
        int p2 = process(cost + del, start - 2, end, add, times, del, limitCoin);
        if (p2 != Integer.MAX_VALUE) {
            min = Math.min(min, p2);
        }
        //让人气*2的方式
        int p3 = process(cost + times, start * 2, end, add, times, del, limitCoin);
        if (p3 != Integer.MAX_VALUE) {
            min = Math.min(min, p3);
        }
        return min;
    }

    public static int minCcoins2(int add, int times, int del, int start, int end) {
        if (start > end) {
            return -1;
        }
        int limitCoin = ((end - start) / 2) * add;
        int limitAim = 2 * end;
        int[][] dp = new int[limitCoin + 1][limitAim + 1];

        for (int cost = 0; cost < limitCoin + 1; cost++) {
            for (int aim = 0; aim < limitAim + 1; aim++) {
                if (aim == end) {
                    dp[cost][aim] = cost;
                } else {
                    dp[cost][aim] = Integer.MAX_VALUE;
                }
            }
        }

        for (int cost = limitCoin; cost >= 0; cost--) {
            for (int aim = 0; aim < limitAim + 1; aim++) {
                if (cost + add <= limitCoin && aim + 2 <= limitAim) {
                    dp[cost][aim] = Math.min(dp[cost][aim], dp[cost + add][aim + 2]);
                }

                if (cost + del <= limitCoin && aim - 2 >= 0) {
                    dp[cost][aim] = Math.min(dp[cost][aim], dp[cost + del][aim - 2]);
                }

                if (cost + times <= limitCoin && aim * 2 <= limitAim) {
                    dp[cost][aim] = Math.min(dp[cost][aim], dp[cost + times][aim * 2]);
                }
            }
        }

        return dp[0][start];
    }

    public static void main(String[] args) {
        int add = 6;
        int times = 5;
        int del = 1;
        int start = 10;
        int end = 30;

        System.out.println(minCcoins2(add, times, del, start, end));
    }
}
