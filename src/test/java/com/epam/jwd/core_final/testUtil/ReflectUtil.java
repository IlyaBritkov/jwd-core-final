package com.epam.jwd.core_final.testUtil;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class ReflectUtil {

    /**
     * Recursive function for reflecting all fields including inherited fields
     **/
    public static List<Field> getAllFields(List<Field> fields, Class<?> clazz) {
        if (clazz.getSuperclass() != null) {
            getAllFields(fields, clazz.getSuperclass());
        }
        fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
        return fields;
    }
}
