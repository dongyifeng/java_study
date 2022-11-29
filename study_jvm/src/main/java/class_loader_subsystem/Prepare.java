package class_loader_subsystem;

public class Prepare {
    // 在 prepare 阶段 a 被赋值为默认值：0
    // 在 initialization 阶段才被赋值为：1
    private static int a = 1;

    public static void main(String[] args) {
        System.out.println(a);
    }
}
