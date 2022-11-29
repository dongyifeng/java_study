package class_loader_subsystem;

import com.sun.net.ssl.internal.ssl.Provider;
import sun.misc.Launcher;
import sun.security.ec.CurveDB;

import java.net.URL;

public class ClassLoaderTest2 {
    public static void main(String[] args) {
        try {
            ClassLoader classLoader = Class.forName("java.lang.String").getClassLoader();
            System.out.println(classLoader);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
