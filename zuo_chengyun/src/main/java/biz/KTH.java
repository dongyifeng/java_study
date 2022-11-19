package biz;


/**
 * 在数据加密和数据压缩中常需要对特殊的字符串进行编码。给定的字母表 A 由 26 个小写字母组成，即 A={a，b ... z}。
 * 该字母表产生的长序字符串是指定字母从左到右出现的次序与字母在字母表中出现的次序相同，且每个字符最多出现1次。
 * 例如：a，b，ab，bc，xyz 等字符串是升序字符串。对字母表 A 产生的所有长度不超过 6 的升序字符串按照字典排列编码如下：
 * a(1)，b(2)，c(3)......,z(26)，ab(27)，ac(28) ...... 对于任意长度不超过 16 的升序字符串，迅速计算出它在上述字典中的编码。
 * <p>
 * 输入：a	；输出：1
 * <p>
 * 输入：b	；输出：2
 * <p>
 * 输入：ab  ；输出：26
 */

public class KTH {
    //

    /**
     * 必须以 i 号（ASCII 码）字符开头、总长度为 len 的字符串有多少个
     *
     * @param i   i 号（ASCII 码）字符开头
     * @param len 总长度为 len 的字符串
     * @return 子序列树
     */
    public static int g(int i, int len) {
        // 长度为 1 的，只有一个字符，就是 i ，只有 1 个字符串
        if (len == 1) {
            return 1;
        }

        int sum = 0;
        for (int j = i + 1; j <= 26; j++) {
            sum += g(j, len - 1);
        }
        return sum;
    }

    /**
     * 长度为 len 有多少个子序列
     *
     * @param len 长度
     * @return 子序列树
     */
    public static int f(int len) {
        int sum = 0;
        for (int i = 1; i <= 26; i++) {
            sum += g(i, len);
        }
        return sum;
    }

    public static int tkh(String s) {
        if (s == null || s.trim().length() == 0) {
            return 0;
        }

        int len = s.length();
        int sum = 0;

        // 长度为 1,2...(len - 1) 子序列数
        for (int i = 1; i < len; i++) {
            sum += f(i);
        }

        char[] chars = s.toCharArray();
        // 长度为 len，第一个字符小于 char[0] 的子序列数
        int first = chars[0] - 'a' + 1;
        for (int i = 1; i < first; i++) {
            sum += g(i, len);
        }

        int pre = first;
        for (int i = 1; i < len; i++) {
            int cur = chars[i] - 'a' + 1;
            for (int j = pre + 1; j < cur; j++) {
                sum += g(j, len - i);
            }
            pre = cur;
        }
        return sum + 1;
    }

    public static void main(String[] args) {
        System.out.println(tkh("djv"));
    }
}
