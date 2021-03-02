package com.epam.jwd.core_final.repository.impl;

import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.domain.factory.impl.Planet;
import com.epam.jwd.core_final.repository.PlanetRepository;
import com.epam.jwd.core_final.util.FileEntityBuilderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Set;

/**
 * Singleton
 **/
public class PlanetRepositoryImpl implements PlanetRepository {
    private final Logger logger = LoggerFactory.getLogger(PlanetRepositoryImpl.class);
    private static PlanetRepositoryImpl INSTANCE;
    private final FileEntityBuilderUtil<Planet> entityBuilder = new FileEntityBuilderUtil<>(Planet.class);

    private PlanetRepositoryImpl() {
    }

    public static PlanetRepositoryImpl getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PlanetRepositoryImpl();
        }
        return INSTANCE;
    }

    @Override
    public Set<Planet> findAll() throws IOException {
        String filePath = NassaContext.getApplicationProperties().getInputRootDir() + "/" + NassaContext.getApplicationProperties().getPlanetsFileName();
        logger.debug("All entities from input file were retrieved");
        return (Set<Planet>) entityBuilder.getCollectionFromFile(filePath);
    }
}
