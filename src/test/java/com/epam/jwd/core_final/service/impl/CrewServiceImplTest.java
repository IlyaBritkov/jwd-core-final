package com.epam.jwd.core_final.service.impl;

import com.epam.jwd.core_final.Main;
import com.epam.jwd.core_final.context.ApplicationContext;
import com.epam.jwd.core_final.criteria.CrewMemberCriteria;
import com.epam.jwd.core_final.domain.Rank;
import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.domain.factory.impl.CrewMember;
import com.epam.jwd.core_final.domain.factory.impl.CrewMemberFactory;
import com.epam.jwd.core_final.exception.EntityCreationException;
import com.epam.jwd.core_final.exception.InvalidStateException;
import com.epam.jwd.core_final.exception.UnreachableSpaceItemException;
import com.epam.jwd.core_final.util.EntityIdGenerator;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class CrewServiceImplTest {
    private final ApplicationContext context = Main.getApplicationContext();
    private final CrewServiceImpl crewService = CrewServiceImpl.getInstance();
    private final CrewMemberFactory crewMemberFactory = CrewMemberFactory.getInstance();

    static {
        try {
            Main.main(null);
        } catch (InvalidStateException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void crewServiceImplShouldBeSingleton() {
        Assertions.assertSame(CrewServiceImpl.getInstance(), CrewServiceImpl.getInstance());
    }

    @Test
    public void methodFindAllCrewMembersShouldReturnAllCrewMemberEntities() throws IOException {
        context.refreshCash(CrewMember.class);

        int expectedEntityAmount = 100;
        int actualEntityAmount = crewService.findAllCrewMembers().size();
        Assertions.assertEquals(expectedEntityAmount, actualEntityAmount);
    }

    @Test
    public void methodFindAllCrewMembersByCriteriaShouldReturnCrewMemberEntitiesByCriteria() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException, InvalidStateException, IOException {
        context.refreshCash(CrewMember.class);


        String expectedName = "Davey Bentley";
        CrewMemberCriteria criteria = CrewMemberCriteria.builder().setName(expectedName).build();
        List<CrewMember> allCrewMembers = crewService.findAllCrewMembersByCriteria(criteria);

        int expectedAmount = 1;
        int actualAmount = allCrewMembers.size();

        Assertions.assertEquals(expectedAmount, actualAmount);

        String actualName = allCrewMembers.get(0).getName();

        Assertions.assertEquals(expectedName, actualName);
    }

    @Test
    public void methodAssignCrewMemberOnMissionShouldNotThrowException() {
        context.refreshCash(CrewMember.class);

        final CrewMember crewMember = crewService.findAllCrewMembers().get(0);
        crewService.assignCrewMemberOnMission(crewMember);

        boolean expectedBol = false;
        boolean actualBol = crewMember.getIsReadyForNextMissions();
        crewMember.setIsReadyForNextMissions(true);
        Assertions.assertEquals(expectedBol, actualBol);
    }

    @Test(expected = UnreachableSpaceItemException.class)
    public void methodAssignCrewMemberOnMissionShouldThrowException() throws InvalidStateException {
        context.emptyAllCash();
        EntityIdGenerator.resetAll();
        context.init();

        final CrewMember crewMember = crewService.findAllCrewMembers().get(0);
        crewService.assignCrewMemberOnMission(crewMember);

        crewService.assignCrewMemberOnMission(crewMember); // should throw an exception
    }

    @Test
    public void methodCreateCrewMemberShouldAddNewCrewMember() throws IOException {
        context.refreshCash(CrewMember.class);

        int expectedSize = context.retrieveBaseEntityList(CrewMember.class).size() + 1;
        final CrewMember crewMember = crewMemberFactory.create(Role.COMMANDER, "Ilya Britkov", Rank.CAPTAIN);
        crewService.createCrewMember(crewMember);

        context.refreshCash(CrewMember.class);

        int actualSize = context.retrieveBaseEntityList(CrewMember.class).size();

        Assertions.assertEquals(expectedSize, actualSize);

        crewService.deleteCrewMember(crewMember);
    }

    @Test(expected = EntityCreationException.class)
    public void methodAddToCashShouldThrowException() throws InvalidStateException {
        context.emptyAllCash();
        EntityIdGenerator.resetAll();
        context.init();

        CrewMember crewMember = crewMemberFactory.create(Role.COMMANDER, "Ilya Britkov", Rank.CAPTAIN);
        crewService.createCrewMember(crewMember);

        crewMember = crewMemberFactory.create(Role.PILOT, "Ilya Britkov", Rank.FIRST_OFFICER);
        // should throw an exception
        crewService.createCrewMember(crewMember);
    }

    @Test
    public void methodDeleteCrewMemberShouldDeleteSpecifiedCrewMember() throws InvalidStateException, IOException {
        context.refreshCash(CrewMember.class);

        final CrewMember crewMember = crewMemberFactory.create(Role.COMMANDER, "Ilya Britkov", Rank.CAPTAIN);
        crewService.createCrewMember(crewMember);

        context.refreshCash(CrewMember.class);

        int expectedSize = context.retrieveBaseEntityList(CrewMember.class).size() - 1;

        crewService.deleteCrewMember(crewMember);

        context.refreshCash(CrewMember.class);

        int actualSize = context.retrieveBaseEntityList(CrewMember.class).size();

        Assertions.assertEquals(expectedSize, actualSize);
    }
}
