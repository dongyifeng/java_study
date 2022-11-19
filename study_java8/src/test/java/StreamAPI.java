import dongyf.study.java.entry.Person;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamAPI {

    @Test
    public void testCreateStream() {
        // 通过 Collection 系列集合提供 stream() 或者 parallelStream()
        List<Integer> list = Arrays.asList(1, 2, 3, 4);
        list.stream();
        list.parallelStream();

        // Arrays.stream()
        Integer[] intArray = new Integer[]{1, 2, 4};
        Arrays.stream(intArray);

        // Stream.of
        Stream.of(intArray);
        IntStream.of(1, 2, 3);
        // [)
        IntStream.range(1, 3).forEach(System.out::println);
        // []
        IntStream.rangeClosed(1, 3).forEach(System.out::println);


        // 创建无限流
        // 迭代：第一次参数初始值
        Stream<Integer> stream = Stream.iterate(0, (x) -> x + 2);
//        stream.forEach(System.out::println);

        // 生成
        Stream.generate(() -> Math.random()).forEach(System.out::println);
    }

    List<Person> persons;

    @Before
    public void initData() {
        persons = new ArrayList<>();
        persons.add(new Person("name1", 10));
        persons.add(new Person("name2", 21));
        persons.add(new Person("name3", 34));
        persons.add(new Person("name4", 6));
        persons.add(new Person("name5", 55));
        persons.add(new Person("name5", 55));
    }

    @Test
    public void testTransform() {
        // 过滤出年龄大于30
        persons.stream().filter((p) -> p.getAge() > 30).forEach(System.out::println);
        System.out.println("----");
        // 断流
        persons.stream().limit(3).forEach(System.out::println);

        // 跳过
        persons.stream().skip(3).forEach(System.out::println);

        System.out.println("----");
        // 去重:hashcode 与 equals
        persons.stream().distinct().forEach(System.out::println);
    }

    @Test
    public void testMap() {
        // 获取所有名字
        persons.stream().map(Person::getName).forEach(System.out::println);

        List<String> core = Arrays.asList("My name is DYF", "What's you name");
        Set<String> word = core.stream().flatMap(doc -> Stream.of(doc.trim().split(" "))).collect(Collectors.toSet());
        System.out.println(word);
    }

    @Test
    public void testSort() {
        // 自然排序
        List<Integer> list = Arrays.asList(1, 3, 5, 6, 2);
        list.stream().sorted().forEach(System.out::println);

        // 定制排序
        persons.stream().sorted((p1, p2) ->
                p1.getAge() == p2.getAge() ? p1.getName().compareTo(p2.getName()) : p1.getAge().compareTo(p2.getAge())
        ).forEach(System.out::println);
    }

    @Test
    public void testFindAndMatch() {
        List<Integer> list = Arrays.asList(1, 3, 5, 6, 2);
        list.stream().max(Integer::compareTo);
        list.stream().min(Integer::compareTo);

        // 查找年龄最小的那个人。有可能找不到，所以返回 Optional
        Optional<Person> first = persons.stream().sorted(Comparator.comparing(Person::getAge)).findFirst();

        // 返回成年中（年龄大于18）任意一个人
        Optional<Person> any = persons.stream().filter(x -> x.getAge() > 18).findAny();

        long count = persons.stream().count();

        Optional<Person> max = persons.stream().max(Comparator.comparing(Person::getAge));
        Optional<Person> min = persons.stream().min(Comparator.comparing(Person::getAge));
    }

    @Test
    public void testReduce() {
        List<Integer> list = Arrays.asList(1, 3, 5, 6, 2);
        Integer sum = list.stream().reduce(0, (x, y) -> x + y);
        Optional<Integer> reduce = list.stream().reduce(Integer::sum);
    }

    @Test
    public void testCollect() {
        Set<String> set = persons.stream().map(Person::getName).collect(Collectors.toSet());
        List<String> list = persons.stream().map(Person::getName).collect(Collectors.toList());
        HashSet<String> hashSet = persons.stream().map(Person::getName).collect(Collectors.toCollection(HashSet::new));
        LinkedHashSet<String> linkedHashSet = persons.stream().map(Person::getName).collect(Collectors.toCollection(LinkedHashSet::new));

        // 分组
        Map<Integer, List<Person>> group = persons.stream().collect(Collectors.groupingBy(Person::getAge));

        // 多级分组
        Map<String, Map<String, List<Person>>> collect = persons.stream().collect(Collectors.groupingBy((p) -> {
            if (p.getAge() <= 35) {
                return "青年";
            } else if (p.getAge() <= 50) {
                return "中年";
            } else {
                return "老年";
            }
        }, Collectors.groupingBy(Person::getName)));

        // 分区
        Map<Boolean, List<Person>> partition = persons.stream().collect(Collectors.partitioningBy(p -> p.getAge() > 60));

        // 总数
        Long count = persons.stream().collect(Collectors.counting());

        // 平均值
        Double mean = persons.stream().collect(Collectors.averagingDouble(Person::getAge));

        // 求和
        Integer sum = persons.stream().collect(Collectors.summingInt(Person::getAge));

        // 最大值
        Optional<Person> maxAgePerson = persons.stream().collect(Collectors.maxBy((p1, p2) -> Integer.compare(p1.getAge(), p2.getAge())));

        // 最小值
        Optional<Person> minAgePerson = persons.stream().collect(Collectors.minBy((p1, p2) -> Integer.compare(p1.getAge(), p2.getAge())));

        // 数据概要
        DoubleSummaryStatistics collect1 = persons.stream().collect(Collectors.summarizingDouble(Person::getAge));


        // 连接字符串
        String str = persons.stream().map(Person::getName).collect(Collectors.joining(",", "===", "----"));
    }
}
