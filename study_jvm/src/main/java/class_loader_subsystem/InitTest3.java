package class_loader_subsystem;

public class InitTest3 {

    static class Father {

        static {
            A = 2;
        }

        public static int A = 1;
    }

    static class Son extends Father {
        public static int B = A;
    }

    public static void main(String[] args) {
        // 加载 Son 之前，要先加载 Father（ loding --> linking --> initialization 全流程），此时 Father.A = 1
        // 加载 Son；Son.B = Father.A = 1
        System.out.println(Son.B);
    }
}
