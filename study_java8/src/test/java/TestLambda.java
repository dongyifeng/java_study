import dongyf.study.java.entry.Person;
import org.junit.Test;

import java.util.*;
import java.util.function.*;

public class TestLambda {
    // Lambda VS 匿名内部类
    @Test
    public void test1() {
        List<Integer> list = Arrays.asList(1, 5, 2, 4, 6, 7);
        // 匿名内部类
        list.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
            }
        });

        // Lambda
        list.sort((o1, o2) -> o1.compareTo(o2));
    }

    @Test
    public void test2() {
        Runnable r = () -> System.out.println("Hello word");

        Consumer<String> consumer = x -> System.out.println(x);
        // 等价于
        Consumer<String> consumer2 = System.out::println;


        Comparator<Integer> com = (x, y) -> {
            System.out.println("Hello comparator");
            return x.compareTo(y);
        };
    }

    @Test
    public void testConsumer() {
        happy(10000, m -> System.out.println("消费" + m));
    }

    public void happy(double money, Consumer<Double> consumer) {
        consumer.accept(money);
    }

    @Test
    public void testSupplier() {
        List<Integer> list = getNumList(10, () -> (int) (Math.random() * 100));
        System.out.println(list);
    }

    public List<Integer> getNumList(int num, Supplier<Integer> supplier) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            list.add(supplier.get());
        }
        return list;
    }

    @Test
    public void testFunction() {
        String newStr = strHandler("\t\t\t Hello world   ", str -> str.trim());
        System.out.println(newStr);
    }

    public String strHandler(String str, Function<String, String> fun) {
        return fun.apply(str);
    }

    @Test
    public void testPredicate() {
        List<String> list = Arrays.asList("Hello", "World", "Hi");
        List<String> stringList = filterStr(list, str -> str.length() > 3);
        System.out.println(stringList);

    }

    public List<String> filterStr(List<String> list, Predicate<String> predicate) {
        List<String> strList = new ArrayList<>();
        for (String str : list) {
            if (predicate.test(str)) {
                strList.add(str);
            }
        }
        return strList;
    }

    @Test
    public void test() {
        Person person = new Person("dyf", 1);
        Supplier<Integer> sup2 = person::getAge;
        Supplier<Person> sup23 = Person::new;

        // 静态方法
        Comparator<Integer> com = Integer::compare;

        BiPredicate<String, String> biPredicate = (x, y) -> x.equals(y);
        // 等价于
        BiPredicate<String, String> biPredicate2 = String::equals;

        // 调用有参构造器
        BiFunction<String,Integer,Person> biFunction = Person::new;


        Function<Integer,Integer[]> fun = (n) -> new Integer[n];
        // 等价于
        Function<Integer,Integer[]> fun2 = Integer[]::new;



    }

}
