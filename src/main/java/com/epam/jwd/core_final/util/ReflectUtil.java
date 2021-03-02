package com.epam.jwd.core_final.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class ReflectUtil {
    private ReflectUtil() {
    }

    /**
     * Return all fields of {@param clazz} including inherited fields
     **/
    public static <T> List<Field> getDeepAllFields(Class<? extends T> clazz) {
        List<Field> fields = new ArrayList<>();
        return getAllDeepFields(fields, clazz);
    }

    /**
     * Return all not null fields of {@param instance} including inherited fields
     **/
    public static <T> List<Field> getDeepNotNullFields(Class<? extends T> clazz, T instance) {
        List<Field> fields = new ArrayList<>();
        getAllDeepFields(fields, clazz);

        fields.forEach(field -> field.setAccessible(true));
        fields.removeIf(field -> {
            try {
                return field.get(instance) == null;
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            return false;
        });

        return fields;
    }

    /**
     * Recursive method for all fields of {@param clazz} retrieving
     **/
    private static List<Field> getAllDeepFields(List<Field> fields, Class<?> clazz) {
        if (clazz.getSuperclass() != null) {
            getAllDeepFields(fields, clazz.getSuperclass());
        }
        fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
        return fields;
    }

    /**
     * @param requiredFields - fields of {@param requiredObject} object,
     *                       which compare with fields {@param comparedFields} of {@param comparedObject} object.
     * @return true if all {@param requiredFields} are equal to {@param comparedFields}
     **/
    public static <T1, T2> boolean compareOnFields(List<Field> requiredFields, T1 requiredObject,
                                                   List<Field> comparedFields, T2 comparedObject) {
        if (requiredFields.size() > comparedFields.size()) {
            return false;
        }

        AtomicBoolean flag = new AtomicBoolean(true);
        requiredFields.forEach(field -> field.setAccessible(true));
        comparedFields.forEach(field -> field.setAccessible(true));

        requiredFields.forEach(requiredField -> {
            try {
                if (!comparedFields.stream().filter(field -> field.getName().equals(requiredField.getName())).findFirst().orElseThrow().get(comparedObject)
                        .equals(requiredField.get(requiredObject))) {
                    flag.set(false);
                }
            } catch (IllegalAccessException e) {
                flag.set(false);
                e.printStackTrace();
            }
        });

        return flag.get();
    }

}
