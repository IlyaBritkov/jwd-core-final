package com.epam.jwd.core_final.repository.impl;

import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.domain.factory.impl.Planet;
import com.epam.jwd.core_final.repository.BaseRepository;
import com.epam.jwd.core_final.util.FileEntityBuilderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collection;

/**
 * Singleton
 **/
public class PlanetRepositoryImpl implements BaseRepository<Planet> {
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
    public Collection<Planet> findAll() throws IOException {
        String filePath = NassaContext.getApplicationProperties().getInputRootDir() + "/" + NassaContext.getApplicationProperties().getPlanetsFileName();
        logger.debug("All entities from input file were retrieved");
        return entityBuilder.getSetFromFile(filePath);
    }
}
