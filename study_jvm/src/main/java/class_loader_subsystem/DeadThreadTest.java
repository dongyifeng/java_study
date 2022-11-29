package class_loader_subsystem;

public class DeadThreadTest {
    public static void main(String[] args) {
        Runnable r = () -> {
            System.out.println(Thread.currentThread().getName() + "开始");
            DeadThread deadThread = new DeadThread();
            System.out.println(Thread.currentThread().getName() + "结束");
        };

        Thread t1 = new Thread(r, "线程1");
        Thread t2 = new Thread(r, "线程2");

        t1.start();
        t2.start();
    }

    static class DeadThread {
        static {
            if (true) {
                // 只会被执行一次
                System.out.println(Thread.currentThread().getName() + "初始化当前类");
                while (true) {

                }
            }
        }
    }
}
