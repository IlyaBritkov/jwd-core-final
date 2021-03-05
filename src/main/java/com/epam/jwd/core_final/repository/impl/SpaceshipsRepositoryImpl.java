package com.epam.jwd.core_final.repository.impl;

import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.domain.factory.impl.Spaceship;
import com.epam.jwd.core_final.repository.SpaceshipsRepository;
import com.epam.jwd.core_final.util.FileEntityBuilderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Set;

/**
 * Singleton
 **/
public class SpaceshipsRepositoryImpl implements SpaceshipsRepository {
    private final Logger logger = LoggerFactory.getLogger(SpaceshipsRepositoryImpl.class);
    private static SpaceshipsRepositoryImpl INSTANCE;
    private final FileEntityBuilderUtil<Spaceship> fileEntityBuilder = new FileEntityBuilderUtil<>(Spaceship.class);
    private final String filePath = NassaContext.getApplicationProperties().getInputRootDir() + File.separator + NassaContext.getApplicationProperties().getSpaceshipsFileName();


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
        return (Set<Spaceship>) fileEntityBuilder.getCollectionFromFile(filePath);
    }

    @Override
    public Spaceship createSpaceship(Spaceship spaceship) throws RuntimeException {
        try {
            return fileEntityBuilder.writeEntityToFile(filePath, spaceship);
        } catch (IOException | URISyntaxException e) {
            logger.error("An exception was thrown: {}", e.toString());
            e.printStackTrace();
        }
        return spaceship;
    }

    @Override
    public Spaceship deleteSpaceship(Spaceship spaceship) {
        try {
            spaceship = fileEntityBuilder.deleteEntityFromFile(filePath, spaceship);
            logger.debug("Spaceship {} was deleted from file", spaceship);
        } catch (IOException | URISyntaxException e) {
            logger.error("An exception was thrown: {}", e.toString());
            e.printStackTrace();
        }
        return spaceship;
    }
}
