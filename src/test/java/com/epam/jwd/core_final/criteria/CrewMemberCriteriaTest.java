package com.epam.jwd.core_final.criteria;

import com.epam.jwd.core_final.domain.Rank;
import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.testUtil.ReflectUtil;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CrewMemberCriteriaTest {
    private final Logger logger = LoggerFactory.getLogger(CrewMemberCriteriaTest.class);

    @Test
    public void builderShouldSetFieldsAndReturnNewInstance() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Long id = 1L;
        String name = "Ilya";
        Role role = Role.COMMANDER;
        Rank rank = Rank.CAPTAIN;
        Boolean isReady = true;

        List<?> expectedValues = Arrays.asList(id, name, role, rank, isReady);

        CrewMemberCriteria crewMemberCriteria =
                CrewMemberCriteria.builder().setId(id).setName(name).
                        setRank(rank).setRole(role).setReadyForNextMissions(isReady).build();


        List<Field> fields = ReflectUtil.getAllFields(new ArrayList<>(), CrewMemberCriteria.class);
        fields.forEach(f -> f.setAccessible(true));

        fields.forEach(field -> {
            try {
                logger.debug("CrewMemberCriteria: {} = {}; ", field.getName(), field.get(crewMemberCriteria));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });

        int i = 0;
        for (Field field : fields) {
            Assertions.assertEquals(expectedValues.get(i++), field.get(crewMemberCriteria));
        }

        Assertions.assertNotSame(crewMemberCriteria, CrewMemberCriteria.builder().build());
    }
}
