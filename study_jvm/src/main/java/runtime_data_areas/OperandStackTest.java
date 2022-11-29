package runtime_data_areas;

public class OperandStackTest {

    public void testAddOperatiion() {
        // byte、short、char、boolean：在局部变量表和操作数栈中  都以 int 型来保存。
        byte i = 15;
        int j = 8;
        int k = i + j;
    }


    public void test() {
        int a = 1;
        int b = 800;
        int c = 800000;
    }

    public void add() {
        // 第一类
        int i1 = 10;
        i1++;

        int i2 = 10;
        ++i2;


        // 第二类
        int i3 = 10;
        int i4 = i3++;

        int i5 = 10;
        int i6 = ++i5;


        // 第三类
        int i7 = 10;
        i7 = i7++;

        int i8 = 10;
        i8 = ++i8;


        // 第四类
        int i9 = 10;
        int i10 = i9++ + i9++;

        int i11 = 10;
        int i12 = ++i11 + ++i11;
    }


    public static void main(String[] args) {

    }
}
