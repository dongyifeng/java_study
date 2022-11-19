package non_linear;

public class BitMap {
    private long[] bits;

    public BitMap(int max) {
        bits = new long[((max + 64) >> 6)];
    }


    public void add(int num) {
        bits[num >> 6] |= (1L << (num & 63));
    }

    public void delete(int num) {
        bits[num >> 6] &= ~(1L << (num & 63));
    }

    public boolean contains(int num) {
        return (bits[num >> 6] & (1L << (num & 63))) != 0;
    }

    public static void main(String[] args) {
        // 32bit * 10 --> 320 bits
        int[] arr = new int[10];

        // 想取得第 178 个 bit 的状态
        int i = 178;

        // 先获取数组的下标
        int numIndex = 178 / 32;
        int bitIndex = 178 % 32;
        System.out.println("bitIndex:" + bitIndex);

        // 拿到 178 位的状态
        int s = ((arr[numIndex] >> (bitIndex)) & 1);
        // int s = ((arr[i / 32] >> (i % 32)) & 1);


        // 请将第 178 位的状态改为 1
        arr[numIndex] = arr[numIndex] | (1 << (bitIndex));

        // 请将第 178 位的状态改为 0
        arr[numIndex] = arr[numIndex] & (~(1 << (bitIndex)));
    }
}
