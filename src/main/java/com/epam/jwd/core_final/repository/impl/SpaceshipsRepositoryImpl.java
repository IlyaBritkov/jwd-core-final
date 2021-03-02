package com.epam.jwd.core_final.repository.impl;

import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.domain.factory.impl.Spaceship;
import com.epam.jwd.core_final.repository.SpaceshipsRepository;
import com.epam.jwd.core_final.util.FileEntityBuilderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Set;

/**
 * Singleton
 **/
public class SpaceshipsRepositoryImpl implements SpaceshipsRepository {
    private final Logger logger = LoggerFactory.getLogger(SpaceshipsRepositoryImpl.class);
    private static SpaceshipsRepositoryImpl INSTANCE;
    private final FileEntityBuilderUtil<Spaceship> entityBuilder = new FileEntityBuilderUtil<>(Spaceship.class);


    private SpaceshipsRepositoryImpl() {
    }

    public static SpaceshipsRepositoryImpl getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SpaceshipsRepositoryImpl();
        }
        return INSTANCE;
    }

    @Override
    public Set<Spaceship> findAll() throws IOException {
        String filePath = NassaContext.getApplicationProperties().getInputRootDir() + "/" + NassaContext.getApplicationProperties().getSpaceshipsFileName();
        logger.debug("All entities from input file were retrieved");
        return (Set<Spaceship>) entityBuilder.getCollectionFromFile(filePath);
    }
}