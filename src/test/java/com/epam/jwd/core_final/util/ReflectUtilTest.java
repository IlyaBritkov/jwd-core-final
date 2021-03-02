package com.epam.jwd.core_final.util;

import com.epam.jwd.core_final.criteria.CrewMemberCriteria;
import com.epam.jwd.core_final.domain.Role;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class ReflectUtilTest {

    @Test
    public void methodGetDeepNotNullFieldsShouldReturnAllNotNullFields() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        CrewMemberCriteria criteria = CrewMemberCriteria.builder().setId(1L).build();
        String expectedField = "id";
        String actualField = ReflectUtil.getDeepNotNullFields(CrewMemberCriteria.class, criteria).get(0).getName();
        Assertions.assertEquals(expectedField, actualField);

        criteria = CrewMemberCriteria.builder().setRole(Role.COMMANDER).build();
        String expectedFieldValue = "COMMANDER";
        String actualFieldValue = ReflectUtil.getDeepNotNullFields(CrewMemberCriteria.class, criteria).get(0).get(criteria).toString();
        Assertions.assertEquals(expectedFieldValue, actualFieldValue);
    }

    @Test
    public void methodGetDeepAllFieldsShouldReturnAllDeepFields() {
        int expectedFieldsAmount = 5;
        List<Field> actualFieldsList = ReflectUtil.getDeepAllFields(CrewMemberCriteria.class);
        Assertions.assertEquals(expectedFieldsAmount, actualFieldsList.size());
    }
}
