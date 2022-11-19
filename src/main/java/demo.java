import com.google.common.collect.Lists;

import java.util.List;

public class demo {
    public static void main(String[] args) {
//        List<String> of = ImmutableList.of("a", "c");
//        of.add("b");
//        System.out.println(of);
        List<String> strings = Lists.newArrayList("a", "c");
        strings.add("b");
        System.out.println(strings);
    }
}
