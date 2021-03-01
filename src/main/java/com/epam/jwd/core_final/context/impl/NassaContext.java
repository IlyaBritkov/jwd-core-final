package com.epam.jwd.core_final.context.impl;

import com.epam.jwd.core_final.context.ApplicationContext;
import com.epam.jwd.core_final.domain.ApplicationProperties;
import com.epam.jwd.core_final.domain.factory.impl.BaseEntity;
import com.epam.jwd.core_final.domain.factory.impl.CrewMember;
import com.epam.jwd.core_final.domain.factory.impl.Planet;
import com.epam.jwd.core_final.domain.factory.impl.Spaceship;
import com.epam.jwd.core_final.exception.InvalidStateException;
import com.epam.jwd.core_final.exception.UnknownEntityException;
import com.epam.jwd.core_final.repository.impl.CrewRepositoryImpl;
import com.epam.jwd.core_final.repository.impl.PlanetRepositoryImpl;
import com.epam.jwd.core_final.repository.impl.SpaceshipsRepositoryImpl;
import com.epam.jwd.core_final.util.PropertyReaderUtil;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.HashSet;

public class NassaContext implements ApplicationContext {
    private final Logger logger = LoggerFactory.getLogger(NassaContext.class);
    @Getter
    private static final ApplicationProperties applicationProperties = new ApplicationProperties(PropertyReaderUtil.getProperties());

    // no getters/setters for them
    private Collection<CrewMember> crewMembers = new HashSet<>();
    private final CrewRepositoryImpl crewRepository = CrewRepositoryImpl.getInstance();

    private Collection<Spaceship> spaceships = new HashSet<>();
    private final SpaceshipsRepositoryImpl spaceshipsRepository = SpaceshipsRepositoryImpl.getInstance();

    private Collection<Planet> planetMap = new HashSet<>();
    private final PlanetRepositoryImpl planetRepository = PlanetRepositoryImpl.getInstance();

    @Override
    @SuppressWarnings("unchecked")
    public <T extends BaseEntity> Collection<T> retrieveBaseEntityList(Class<T> tClass) {
        Collection<T> collection = null;
        if (tClass == CrewMember.class) {
            collection = (Collection<T>) crewMembers;
        } else if (tClass == Spaceship.class) {
            collection = (Collection<T>) spaceships;
        } else if (tClass == Planet.class) {
            collection = (Collection<T>) planetMap;
        }
        logger.debug("Collection with {} entities was retrieved", tClass.getSimpleName());
        return collection;
    }

    /**
     * You have to read input files, populate collections
     *
     * @throws InvalidStateException
     */

    @Override
    public void init() throws InvalidStateException {
        try {
            crewMembers = crewRepository.findAll();
            spaceships = spaceshipsRepository.findAll();
            planetMap = planetRepository.findAll();
            logger.info("All cashes were initialized");
        } catch (Exception e) {

            throw new InvalidStateException();
        }
    }

    public void emptyAllCash() {
        emptyCash(CrewMember.class);
        emptyCash(Spaceship.class);
        emptyCash(Planet.class);
        logger.info("All cashes were deleted");
    }

    public <T extends BaseEntity> void emptyCash(Class<T> tClass) {
        if (tClass == CrewMember.class) {
            crewMembers = new HashSet<>();
        } else if (tClass == Spaceship.class) {
            spaceships = new HashSet<>();
        } else if (tClass == Planet.class) {
            planetMap = new HashSet<>();
        } else {
            throw new UnknownEntityException(tClass.getSimpleName());
        }
        logger.info("Cash of {} was deleted", tClass.getSimpleName());
    }


}
