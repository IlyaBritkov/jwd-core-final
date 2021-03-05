package com.epam.jwd.core_final.service.impl;

import com.epam.jwd.core_final.Main;
import com.epam.jwd.core_final.context.ApplicationContext;
import com.epam.jwd.core_final.criteria.SpaceshipCriteria;
import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.domain.factory.impl.Spaceship;
import com.epam.jwd.core_final.domain.factory.impl.SpaceshipFactory;
import com.epam.jwd.core_final.exception.InvalidStateException;
import com.epam.jwd.core_final.exception.UnreachableSpaceItemException;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpaceshipsServiceImplTest {
    private final ApplicationContext context = Main.getApplicationContext();
    private final SpaceshipServiceImpl spaceshipService = SpaceshipServiceImpl.getInstance();
    private final SpaceshipFactory spaceshipFactory = SpaceshipFactory.getInstance();

    static {
        try {
            Main.main(null);
        } catch (InvalidStateException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void spaceshipsServiceImplShouldBeSingleton() {
        Assertions.assertSame(SpaceshipServiceImpl.getInstance(), SpaceshipServiceImpl.getInstance());
    }

    @Test
    public void methodFindAllSpaceshipsShouldReturnAllSpaceshipEntities() throws IOException {
        context.refreshCash(Spaceship.class);

        int expectedEntityAmount = 30;
        int actualEntityAmount = spaceshipService.findAllSpaceships().size();
        Assertions.assertEquals(expectedEntityAmount, actualEntityAmount);
    }

    @Test
    public void methodFindAllSpaceshipsByCriteriaShouldReturnSpaceshipEntitiesByCriteria() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException, InvalidStateException, IOException {
        context.refreshCash(Spaceship.class);

        String expectedName = "Challenger";
        SpaceshipCriteria criteria = SpaceshipCriteria.builder().setName(expectedName).build();
        List<Spaceship> allSpaceships = spaceshipService.findAllSpaceshipsByCriteria(criteria);

        int expectedAmount = 1;
        int actualAmount = allSpaceships.size();

        Assertions.assertEquals(expectedAmount, actualAmount);

        String actualName = allSpaceships.get(0).getName();

        Assertions.assertEquals(expectedName, actualName);
    }

    @Test
    public void methodAssignSpaceshipOnMissionShouldNotThrowException() throws InvalidStateException, IOException {
        context.refreshCash(Spaceship.class);

        final Spaceship spaceship = spaceshipService.findAllSpaceships().get(0);
        spaceshipService.assignSpaceshipOnMission(spaceship);

        boolean expectedBol = false;
        Assertions.assertEquals(expectedBol, spaceship.getIsReadyForNextMissions());
    }

    @Test(expected = UnreachableSpaceItemException.class)
    public void methodAssignSpaceshipOnMissionShouldThrowException() throws InvalidStateException, IOException {
        context.refreshCash(Spaceship.class);

        final Spaceship spaceship = spaceshipService.findAllSpaceships().get(0);
        spaceshipService.assignSpaceshipOnMission(spaceship);

        spaceshipService.assignSpaceshipOnMission(spaceship); // should throw an exception
    }

    @Test
    public void methodCreateSpaceshipShouldAddNewSpaceship() throws IOException, InvalidStateException {
        context.refreshCash(Spaceship.class);

        int expectedSize = context.retrieveBaseEntityList(Spaceship.class).size() + 1;

        Map<Role, Short> crew = new HashMap<>();
        crew.put(Role.COMMANDER, (short) 1);
        crew.put(Role.PILOT, (short) 2);
        crew.put(Role.MISSION_SPECIALIST, (short) 1);
        crew.put(Role.FLIGHT_ENGINEER, (short) 2);
        final Spaceship spaceship = spaceshipFactory.create("Camry 3.5", 1000L, crew);

        spaceshipService.createSpaceship(spaceship);
        context.refreshCash(Spaceship.class);
        int actualSize = context.retrieveBaseEntityList(Spaceship.class).size();

        Assertions.assertEquals(expectedSize, actualSize);

        spaceshipService.deleteSpaceship(spaceship);
    }

    @Test
    public void methodDeleteCrewMemberShouldDeleteSpecifiedCrewMember() throws IOException, InvalidStateException {
        context.refreshCash(Spaceship.class);

        Map<Role, Short> crew = new HashMap<>();
        crew.put(Role.COMMANDER, (short) 1);
        crew.put(Role.PILOT, (short) 2);
        crew.put(Role.MISSION_SPECIALIST, (short) 1);
        crew.put(Role.FLIGHT_ENGINEER, (short) 2);
        final Spaceship spaceship = spaceshipFactory.create("Camry 3.5", 1000L, crew);

        spaceshipService.createSpaceship(spaceship);

        context.refreshCash(Spaceship.class);

        int expectedSize = context.retrieveBaseEntityList(Spaceship.class).size() - 1;

        spaceshipService.deleteSpaceship(spaceship);

        context.refreshCash(Spaceship.class);

        int actualSize = context.retrieveBaseEntityList(Spaceship.class).size();

        Assertions.assertEquals(expectedSize, actualSize);
    }
}
