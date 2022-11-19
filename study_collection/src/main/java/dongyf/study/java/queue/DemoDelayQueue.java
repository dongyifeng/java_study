package dongyf.study.java.queue;

import java.util.concurrent.DelayQueue;

public class DemoDelayQueue {

    DelayQueue<DelayTask> queue = new DelayQueue();

    public void add(String name) {
        queue.add(new DelayTask<String>(s -> {
            System.out.println(System.currentTimeMillis() + ":" + s);
        }, name));
    }

    public void run() throws InterruptedException {
        while (true) {
            DelayTask s = queue.take(); // 必要时进行阻塞等待
            s.excute();
            System.out.println(s);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        DemoDelayQueue queue = new DemoDelayQueue();
        System.out.println(System.currentTimeMillis());

        queue.add("dyf");
        queue.run();
        System.out.println(System.currentTimeMillis() + ":Over");
    }
}
