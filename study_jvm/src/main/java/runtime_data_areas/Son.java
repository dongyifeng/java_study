package runtime_data_areas;


class Father {
    public Father() {
        System.out.println("father 的构造器");
    }

    public static void showStatic(String str) {
        System.out.println("father " + str);
    }

    public final void showFinal() {
        System.out.println("father show final");
    }

    public void showCommon() {
        System.out.println("father 普通方法");
    }
}


public class Son extends Father {
    public Son() {
        super();
    }

    public Son(int age) {
        this();
    }

    // 不是重写父类的静态方法，因为静态方法不能被重写
    public static void showStatic(String str) {
        System.out.println("son " + str);
    }

    private final void showPrivate(String str) {
        System.out.println("son private " + str);
    }

    public void show() {
        showStatic("dyf");  // 调用子类
        super.showStatic("good"); // 调用父类
        showPrivate("hello");  // 调用子类
        super.showCommon();    // 调用父类
        showFinal();            // 调用父类
        showCommon();           // 调用父类
        info();
    }

    public void info() {

    }

    public static void main(String[] args) {
        Son son = new Son();
        son.show();
    }

}
