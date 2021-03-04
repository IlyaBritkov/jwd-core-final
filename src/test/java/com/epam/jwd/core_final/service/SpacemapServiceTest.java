package com.epam.jwd.core_final.service;

import com.epam.jwd.core_final.Main;
import com.epam.jwd.core_final.domain.factory.impl.Planet;
import com.epam.jwd.core_final.domain.factory.impl.PlanetFactory;
import com.epam.jwd.core_final.exception.InvalidStateException;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class SpacemapServiceTest {
    private final PlanetFactory planetFactory = PlanetFactory.getInstance();

    static {
        try {
            Main.main(null);
        } catch (InvalidStateException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void methodGetDistanceBetweenPlanetsShouldCalculateRightDistance() {
        Planet firstPlanet = planetFactory.create("Earth", 1, 1);
        Planet secondPlanet = planetFactory.create("Lune", 2, 2);

        int expectedValue = (int) Math.sqrt(2);
        int actualValue = SpacemapService.getDistanceBetweenPlanets(firstPlanet, secondPlanet);

        Assertions.assertEquals(expectedValue, actualValue);
    }
}
