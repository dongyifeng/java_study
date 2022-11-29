package class_loader_subsystem;

public class StringTest {
    public static void main(String[] args) {
        java.lang.String str = new java.lang.String();
        System.out.println("Hello world!");
        System.out.println(StringTest.class.getClassLoader());
    }
}
