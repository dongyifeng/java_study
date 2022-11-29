package java.lang;

public class String {
    static {
        System.out.println("我是自定义的 String 类的静态代码块");
    }

    public static void main(String[] args) {
        System.out.printf("Hello String");
    }
}
