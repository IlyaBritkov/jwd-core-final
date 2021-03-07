package com.epam.jwd.core_final.criteria;

import com.epam.jwd.core_final.domain.MissionStatus;
import com.epam.jwd.core_final.testUtil.ReflectUtil;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FlightMissionCriteriaTest {
    private final Logger logger = LoggerFactory.getLogger(FlightMissionCriteriaTest.class);

    @Test
    public void builderShouldSetFieldsAndReturnNewInstance() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Long id = 1L;
        String name = "SpaceX";
        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = startDate.plusWeeks(3);
        Long distance = 1000L;
        MissionStatus missionStatus = MissionStatus.IN_PROGRESS;

        List<?> expectedValues = Arrays.asList(id, name, startDate, endDate, distance, missionStatus);

        FlightMissionCriteria flightMissionCriteria = FlightMissionCriteria.builder().
                setId(id).setName(name).setStartDate(startDate).setEndDate(endDate).setDistance(distance).setMissionStatus(missionStatus).build();


        List<Field> fields = ReflectUtil.getAllFields(new ArrayList<>(), FlightMissionCriteria.class);
        fields.forEach(f -> f.setAccessible(true));

        fields.forEach(field -> {
            try {
                logger.debug("flightMissionCriteria: {} = {}; ", field.getName(), field.get(flightMissionCriteria));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });

        int i = 0;
        for (Field field : fields) {
            if (field.get(flightMissionCriteria) != null) {
                Assertions.assertEquals(expectedValues.get(i++), field.get(flightMissionCriteria));
            }
        }

        Assertions.assertNotSame(flightMissionCriteria, FlightMissionCriteria.builder().build());
    }
}
