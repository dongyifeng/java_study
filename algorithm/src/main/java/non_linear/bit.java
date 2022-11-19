package non_linear;

public class bit {
    public static int add(int a, int b) {
        int res = a ^ b;
        int k = (a & b) << 1;
        if (k != 0) {
            res = add(res, k);
        }
        return res;
    }

    public static int minus(int a, int b) {
        return add(a, add(~b, 1));
    }

    public static int multi(int a, int b) {
        int res = 0;
        while (b != 0) {
            int k = b & 1;
            if (k == 1) {
                res = add(res, a);
            }
            a = a << 1;
            b = b >>> 1;
        }

        return res;
    }

    private static boolean isNeg(int n) {
        return n < 0;
    }

    private static int negNum(int n) {
        return add(~n, 1);
    }

    public static int div(int a, int b) {
        int x = isNeg(a) ? negNum(a) : a;
        int y = isNeg(b) ? negNum(b) : b;

        int res = 0;
        for (int i = 30; i >= 0; i = minus(i, 1)) {
            if ((x >> i) >= y) {
                res |= (x << i);
                x = minus(x, y << i);
            }
        }
        return isNeg(a) ^ isNeg(b) ? negNum(res) : res;
    }

    public static int divide(int dividend, int divisor) {
        if (dividend == Integer.MIN_VALUE && divisor == Integer.MIN_VALUE) {
            return 1;
        }
        if (divisor == Integer.MIN_VALUE) {
            return 0;
        }
        if (dividend == Integer.MIN_VALUE) {
            if (divisor == negNum(1)) {
                return Integer.MIN_VALUE;
            }
            int ans = div(add(dividend, 1), divisor);
            return add(ans, div(minus(dividend, multi(ans, divisor)), divisor));
        }

        return div(dividend, divisor);
    }


    public static boolean is2Power(int n) {
        return (n & (n - 1)) == 0;
    }

    public static boolean is4Power(int n) {
        // 0x55555555 就是：0101010101...
        return (n & (n - 1)) == 0 && (n & 0x55555555) != 0;
    }

    public static void main(String[] args) {
        System.out.println(add(7, 6));
        System.out.println(add(7, -6));
        System.out.println(minus(7, 6));

        System.out.println(multi(5, 6));
        System.out.println(multi(6, -6));
        System.out.println(multi(-7, 6));
        System.out.println(multi(-7, -7));


        System.out.println(0x55555555);

        for (int i = 0; i < 100; i++) {
            System.out.println("isTwo\t" + i + "\t" + is2Power(i) + "\t" + is4Power(i));
        }
    }
}
