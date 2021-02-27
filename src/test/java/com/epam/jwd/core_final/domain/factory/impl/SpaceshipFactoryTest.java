package com.epam.jwd.core_final.domain.factory.impl;

import com.epam.jwd.core_final.domain.Role;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.HashMap;
import java.util.Map;

public class SpaceshipFactoryTest {
    @Test
    public void factoryShouldBeSingleton() {
        Assertions.assertSame(SpaceshipFactory.getInstance(), SpaceshipFactory.getInstance());
    }

    @Test
    public void methodCreateShouldReturnNewEntities() {
        SpaceshipFactory factory = SpaceshipFactory.getInstance();
        Map<Role, Short> crew = new HashMap<>();
        crew.put(Role.PILOT, (short) 2);
        crew.put(Role.FLIGHT_ENGINEER, (short) 2);
        crew.put(Role.MISSION_SPECIALIST, (short) 1);
        crew.put(Role.COMMANDER, (short) 1);

        Spaceship first = factory.create("Snapdragon", crew, 1000L);
        Spaceship second = factory.create("Horizon", crew, 2000L);
        Assertions.assertNotSame(first, second);
        Assertions.assertNotEquals(first, second);
    }

    @Test
    public void methodCreateShouldReturnNewEntityWithAppropriateFields() {
        String name = "Snapdragon";

        Map<Role, Short> crew = new HashMap<>();
        crew.put(Role.PILOT, (short) 2);
        crew.put(Role.FLIGHT_ENGINEER, (short) 2);
        crew.put(Role.MISSION_SPECIALIST, (short) 1);
        crew.put(Role.COMMANDER, (short) 1);

        Long flightDistance = 1000L;

        SpaceshipFactory factory = SpaceshipFactory.getInstance();
        Spaceship spaceship = factory.create(name, crew, flightDistance);
        Assertions.assertEquals(name, spaceship.getName());
        Assertions.assertEquals(crew, spaceship.getCrew());
        Assertions.assertEquals(flightDistance, spaceship.getFlightDistance());
    }
}
