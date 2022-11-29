package class_loader_subsystem;

import com.sun.net.ssl.internal.ssl.Provider;
import sun.awt.geom.Curve;
import sun.misc.Launcher;
import sun.security.ec.CurveDB;

import java.net.URL;

public class ClassLoaderTest1 {
    public static void main(String[] args) {
        // 获取 BootstrapClassLoader 能够加载的 api 的路径
        URL[] urLs = Launcher.getBootstrapClassPath().getURLs();
        for (URL url : urLs) {
            System.out.println(url.toExternalForm());
        }
        /*
        file:/Library/Java/JavaVirtualMachines/jdk1.8.0_191.jdk/Contents/Home/jre/lib/resources.jar
        file:/Library/Java/JavaVirtualMachines/jdk1.8.0_191.jdk/Contents/Home/jre/lib/rt.jar
        file:/Library/Java/JavaVirtualMachines/jdk1.8.0_191.jdk/Contents/Home/jre/lib/sunrsasign.jar
        file:/Library/Java/JavaVirtualMachines/jdk1.8.0_191.jdk/Contents/Home/jre/lib/jsse.jar
        file:/Library/Java/JavaVirtualMachines/jdk1.8.0_191.jdk/Contents/Home/jre/lib/jce.jar
        file:/Library/Java/JavaVirtualMachines/jdk1.8.0_191.jdk/Contents/Home/jre/lib/charsets.jar
        file:/Library/Java/JavaVirtualMachines/jdk1.8.0_191.jdk/Contents/Home/jre/lib/jfr.jar
        file:/Library/Java/JavaVirtualMachines/jdk1.8.0_191.jdk/Contents/Home/jre/classes
         */

        // 从上面的路径中随意选择一个类
        ClassLoader classLoader = Provider.class.getClassLoader();
        System.out.println(classLoader);
        // null


        System.out.println("----------------------");

        // 扩展类加载器
//        String property = System.getProperty("java.ext.dirs");
//        for (String path : property.split(":")) {
//            System.out.println(path);
//        }
        /*
        /Users/dongyf/Library/Java/Extensions
        /Library/Java/JavaVirtualMachines/jdk1.8.0_191.jdk/Contents/Home/jre/lib/ext
        /Library/Java/Extensions
        /Network/Library/Java/Extensions
        /System/Library/Java/Extensions
        /usr/lib/java
         */

        // 从上面的路径中随意选择一个类
        ClassLoader classLoader1 = CurveDB.class.getClassLoader();
        System.out.println(classLoader1);
        // sun.misc.Launcher$ExtClassLoader@27c170f0
    }
}
