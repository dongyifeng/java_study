package dongyf.study.java.fork_join;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.stream.LongStream;

public class TestForkJoin {
    public static void main(String[] args) {
        Instant start = Instant.now();

        ForkJoinPool pool = new ForkJoinPool();
        ForkJoinTask<Long> task = new ForkJoinCalculate(0, 1000000000L);
        Long sum = pool.invoke(task);
        System.out.println(sum);
        System.out.println(Duration.between(start, Instant.now()).toMillis());


        long sum2 = LongStream.rangeClosed(0, 1000000000L).parallel().reduce(0, Long::sum);
    }
}
