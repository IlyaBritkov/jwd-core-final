package com.epam.jwd.core_final.service.impl;

import com.epam.jwd.core_final.Main;
import com.epam.jwd.core_final.context.ApplicationContext;
import com.epam.jwd.core_final.domain.CashedEntity;
import com.epam.jwd.core_final.domain.factory.impl.Planet;
import com.epam.jwd.core_final.service.SpacemapService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Singleton
 **/
public class SpacemapServiceImpl implements SpacemapService {
    private final Logger logger = LoggerFactory.getLogger(SpacemapServiceImpl.class);
    private static SpacemapServiceImpl INSTANCE;
    private final ApplicationContext context = Main.getApplicationContext();

    private SpacemapServiceImpl() {
    }

    public static SpacemapServiceImpl getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SpacemapServiceImpl();
        }
        return INSTANCE;
    }

    @Override
    public Planet getRandomPlanet() {
        List<Planet> planets = context.retrieveBaseEntityList(Planet.class).stream()
                .map(CashedEntity::getEntity).collect(Collectors.toList());
        int randomIndex = new Random().nextInt(planets.size());
        final Planet randomPlanet = planets.get(randomIndex);
        logger.debug("Random planet was retrieved: {}", randomPlanet);
        return randomPlanet;
    }
}
