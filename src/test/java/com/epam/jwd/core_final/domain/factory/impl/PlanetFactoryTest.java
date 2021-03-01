package com.epam.jwd.core_final.domain.factory.impl;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class PlanetFactoryTest {
    @Test
    public void factoryShouldBeSingleton() {
        Assertions.assertSame(PlanetFactory.getInstance(), PlanetFactory.getInstance());
    }

    @Test
    public void methodCreateShouldReturnNewEntities() {
        String name = "Earth";
        Integer x = 1000;
        Integer y = 1000;
        PlanetFactory factory = PlanetFactory.getInstance();

        Planet first = factory.create(name, x, y);
        Planet second = factory.create(name, x, y);
        Assertions.assertNotSame(first, second);
    }

    @Test
    public void methodCreateShouldReturnNewEntityWithAppropriateFields() {
        String name = "Earth";
        Integer x = 1000;
        Integer y = 1000;
        PlanetFactory factory = PlanetFactory.getInstance();

        Planet planet = factory.create(name, x, y);
        Assertions.assertEquals(name, planet.getName());
        Assertions.assertEquals(x, planet.getLocation().getX());
        Assertions.assertEquals(y, planet.getLocation().getY());
    }
}
