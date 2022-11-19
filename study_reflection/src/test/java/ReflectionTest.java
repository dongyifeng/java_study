import org.junit.Test;
import reflection.entry.Person;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectionTest {
    @Test
    public void test() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchFieldException {

        // 通过反射创建 Person 对象
        Class<Person> personClass = Person.class;
        Constructor<Person> constructor = personClass.getConstructor(String.class, int.class);
        Person tom = constructor.newInstance("Tom", 12);
        System.out.println(tom.toString());

        // 通过反射：调用属性
        Field age = personClass.getDeclaredField("age");
        age.set(tom, 10);
        System.out.println(tom.toString());

        // 通过反射：调用方法
        Method show = personClass.getDeclaredMethod("show");
        show.invoke(tom);

        // 通过反射：调用私有的构造器,私有方法,私有属性。
        Constructor<Person> personClassConstructor = personClass.getDeclaredConstructor(String.class);
        // setAccessible启动和禁用访问安全检查的开关
        // true：使得原本无法访问的私有成员也可以访问。
        personClassConstructor.setAccessible(true);
        Person tim = personClassConstructor.newInstance("Tim");
        System.out.println(tim);

    }
}
