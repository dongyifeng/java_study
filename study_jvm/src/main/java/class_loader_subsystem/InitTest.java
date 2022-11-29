package class_loader_subsystem;

public class InitTest {

    static {
        num = 20;
        // 报错：非法前向引用。
//        System.out.println(num);
    }

    // linking 的 prepare: num = 0
    // initialization num = 20; --> num = 10
    private static int num = 10;

    public static void main(String[] args) {
        System.out.println(num);


    }
}
