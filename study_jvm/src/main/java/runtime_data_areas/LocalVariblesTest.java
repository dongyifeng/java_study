package runtime_data_areas;

import javax.xml.crypto.Data;
import java.util.Date;

public class LocalVariblesTest {
    private int count = 0;

    public void test() {
        Date date = new Date();
        String name = "dyf";
        String info = test1(date, name);
        System.out.println(date + name);
    }

    public String test1(Date dateP, String name2) {
        dateP = null;
        name2 = "dongyifeng";
        double weight = 0.6;
        char gender = '男';
        return dateP + name2;
    }

    public static void main(String[] args) {
        LocalVariblesTest test = new LocalVariblesTest();
        int num = 10;
        test.test();
    }

    public static void testStatic() {
        // 这里编译错误：因为 this 变量不存在于当前方法的局部变量表中。
//        System.out.println(this.count);
    }

    public void test4() {
        int a = 0;
        {
            int b = 0;
            b = a + 1;
        }
        // 变量 c 使用之前已经销毁的变量 b 的 slot 位置
        int c = a + 1;
    }


    public void test5() {
        int num;
        // 错误信息：变量 num 未进行初始化
//        System.out.println(num);
    }
}











