package com.epam.jwd.core_final.service.impl;

import com.epam.jwd.core_final.Main;
import com.epam.jwd.core_final.context.ApplicationContext;
import com.epam.jwd.core_final.criteria.CrewMemberCriteria;
import com.epam.jwd.core_final.domain.factory.impl.CrewMember;
import com.epam.jwd.core_final.exception.InvalidStateException;
import com.epam.jwd.core_final.util.EntityIdGenerator;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class CrewServiceImplTest {
    private final CrewServiceImpl crewService = CrewServiceImpl.getInstance();
    private final ApplicationContext applicationContext = Main.getApplicationContext();

    static {
        try {
            Main.main(null);
        } catch (InvalidStateException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void methodFindAllCrewMembersShouldReturnAllCrewMemberEntities() throws InvalidStateException {
        applicationContext.emptyAllCash();
        applicationContext.init();
        int expectedEntityAmount = 100;
        int actualEntityAmount = crewService.findAllCrewMembers().size();
        Assertions.assertEquals(expectedEntityAmount, actualEntityAmount);
    }

    @Test
    public void methodFindAllCrewMembersByCriteriaShouldReturnCrewMemberEntitiesByCriteria() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException, InvalidStateException {
        applicationContext.emptyAllCash();
        EntityIdGenerator.resetAll();
        applicationContext.init();

        CrewMemberCriteria criteria = CrewMemberCriteria.builder().setId(1L).build();
        List<CrewMember> allCrewMembers = crewService.findAllCrewMembersByCriteria(criteria);

        int expectedAmount = 1;
        int actualAmount = allCrewMembers.size();

        Assertions.assertEquals(expectedAmount, actualAmount);


        long expectedValue = 1L;
        long actualValue = allCrewMembers.get(0).getId();

        Assertions.assertEquals(expectedValue, actualValue);



        String expectedName = "Davey Bentley";
        criteria = CrewMemberCriteria.builder().setName(expectedName).build();
        allCrewMembers = crewService.findAllCrewMembersByCriteria(criteria);

        expectedAmount = 1;
        actualAmount = allCrewMembers.size();

        Assertions.assertEquals(expectedAmount, actualAmount);

        String actualName = allCrewMembers.get(0).getName();

        Assertions.assertEquals(expectedName, actualName);
    }
}
