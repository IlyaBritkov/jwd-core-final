package com.epam.jwd.core_final.criteria;

import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.testUtil.ReflectUtil;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class SpaceshipCriteriaTest {
    private final Logger logger = LoggerFactory.getLogger(SpaceshipCriteriaTest.class);

    @Test
    public void builderShouldSetFieldsAndReturnNewInstance() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Long id = 1L;
        String name = "Dragon";

        Map<Role, Short> crew = new HashMap<>();
        crew.put(Role.PILOT, (short) 2);
        crew.put(Role.FLIGHT_ENGINEER, (short) 2);
        crew.put(Role.MISSION_SPECIALIST, (short) 1);
        crew.put(Role.COMMANDER, (short) 1);

        Long flightDistance = 10_000L;
        Boolean isReadyForNextMissions = true;

        List<?> expectedValues = Arrays.asList(id, name, crew, flightDistance, isReadyForNextMissions);

        SpaceshipCriteria spaceshipCriteria = SpaceshipCriteria.builder().
                setId(id).setName(name).setCrew(crew).setFlightDistance(flightDistance).setReadyForNextMissions(isReadyForNextMissions).build();

        List<Field> fields = ReflectUtil.getAllFields(new ArrayList<>(), SpaceshipCriteria.class);
        fields.forEach(f -> f.setAccessible(true));

        fields.forEach(field -> {
            try {
                logger.debug("spaceshipCriteria: {} = {}; ", field.getName(), field.get(spaceshipCriteria));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });

        int i = 0;
        for (Field field : fields) {
            if (field.get(spaceshipCriteria) != null) {
                Assertions.assertEquals(expectedValues.get(i++), field.get(spaceshipCriteria));
            }
        }

        Assertions.assertNotSame(spaceshipCriteria, SpaceshipCriteria.builder().build());
    }

}
