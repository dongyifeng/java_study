package tmp;

import org.junit.Test;

public class OtherTest {
    @Test
    public void test() {
        String word = "你好，Hello";

        char[] chars = word.toLowerCase().toCharArray();
        for (Character key : chars) {
            System.out.println(key);
        }
    }
}
