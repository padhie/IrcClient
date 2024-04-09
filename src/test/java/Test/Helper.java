package Test;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;

public class Helper {
    public static void setProtectedProperty (Object object, String property, Object value) {
        Field field = null;

        try {
            field = object.getClass().getDeclaredField(property);
        } catch (NoSuchFieldException exteption) {
            try {
                field = object.getClass().getSuperclass().getDeclaredField(property);
            } catch (NoSuchFieldException exception) {
                exception.printStackTrace();
            }
        }

        if (field == null) {
            System.out.print("Field can't be find");
            System.exit(0);
        }

        try {
            field.setAccessible(true);
            field.set(object, value);
            field.setAccessible(false);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static Object getProtectedProperty (Object object, String property) {
        Field field = null;

        try {
            field = object.getClass().getDeclaredField(property);
        } catch (NoSuchFieldException exteption) {
            try {
                field = object.getClass().getSuperclass().getDeclaredField(property);
            } catch (NoSuchFieldException exception) {
                exception.printStackTrace();
            }
        }

        if (field == null) {
            System.out.print("Field can't be find.");
            System.exit(0);
        }

        try {
            field.setAccessible(true);
            return (Object) field.get(object);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Method getProtectedMethod (Object object, String methodName, Class<?>... parameterTypes) {
        Method method = null;
        try {
            method = object.getClass().getDeclaredMethod(methodName, parameterTypes);
        } catch (NoSuchMethodException exception) {
            exception.printStackTrace();
        }

        if (method == null) {
            System.out.print("Method not found");
            System.exit(0);
        }

        method.setAccessible(true);

        return method;
    }

    public static String loadFixture (String file) {
        String cwd = new File("").getAbsolutePath();

        Path path = Path.of(cwd + "/src/test/resources/Fixture/" + file);

        try {
            return Files.readString(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
