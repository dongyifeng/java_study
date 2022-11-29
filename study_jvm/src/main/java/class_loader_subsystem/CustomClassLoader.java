package class_loader_subsystem;

import java.io.FileNotFoundException;

public class CustomClassLoader extends ClassLoader {

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        try {
            byte[] result = getClassFromCustomPath(name);
            if (result == null) {
                throw new FileNotFoundException();
            } else {
                return defineClass(name, result, 0, result.length);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 从自定义路径中加载指定类
    private byte[] getClassFromCustomPath(String name) {
        // 如果指定路径的字节码文件进行了加密，在此处解密
        return null;
    }
}
