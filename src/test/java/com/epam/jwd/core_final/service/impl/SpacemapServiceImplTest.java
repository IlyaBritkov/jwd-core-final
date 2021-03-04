package com.epam.jwd.core_final.service.impl;

import com.epam.jwd.core_final.Main;
import com.epam.jwd.core_final.context.ApplicationContext;
import com.epam.jwd.core_final.domain.factory.impl.Planet;
import com.epam.jwd.core_final.domain.factory.impl.PlanetFactory;
import com.epam.jwd.core_final.exception.InvalidStateException;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class SpacemapServiceImplTest {
    private final ApplicationContext context = Main.getApplicationContext();
    private final SpacemapServiceImpl spacemapService = SpacemapServiceImpl.getInstance();
    private final PlanetFactory planetFactory = PlanetFactory.getInstance();

    static {
        try {
            Main.main(null);
        } catch (InvalidStateException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void spacemapServiceImplShouldBeSingleton() {
        Assertions.assertSame(SpacemapServiceImpl.getInstance(), SpacemapServiceImpl.getInstance());
    }

    @Test
    public void methodGetRandomPlanetShouldRetrievePlanet() {
        Planet planet = spacemapService.getRandomPlanet();
        if (planet == null) {
            Assertions.fail();
        }
    }
}
