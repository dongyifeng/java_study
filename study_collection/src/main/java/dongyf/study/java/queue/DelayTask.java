package dongyf.study.java.queue;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class DelayTask<T> implements Delayed {

    private long expire = 10000;
    private Consumer<T> consumer;
    private T t;

    public DelayTask(Consumer<T> consumer, T t) {
        this.consumer = consumer;
        this.t = t;
        this.expire += System.currentTimeMillis();
    }


    public void excute() {
        consumer.accept(t);
    }

    // 此方法的实现用于定义优先级
    public long getDelay(TimeUnit unit) {
        return unit.convert(this.expire - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }

    // 这里返回的是剩余延时，当延时为0时，此元素延时期满，可从take()取出
    public int compareTo(Delayed o) {
        return 0;
    }
}
