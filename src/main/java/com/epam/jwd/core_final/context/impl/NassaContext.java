package com.epam.jwd.core_final.context.impl;

import com.epam.jwd.core_final.context.ApplicationContext;
import com.epam.jwd.core_final.domain.ApplicationProperties;
import com.epam.jwd.core_final.domain.factory.impl.BaseEntity;
import com.epam.jwd.core_final.domain.factory.impl.CrewMember;
import com.epam.jwd.core_final.domain.factory.impl.Planet;
import com.epam.jwd.core_final.domain.factory.impl.Spaceship;
import com.epam.jwd.core_final.exception.InvalidStateException;
import com.epam.jwd.core_final.exception.UnknownEntityException;
import com.epam.jwd.core_final.repository.CrewRepository;
import com.epam.jwd.core_final.repository.PlanetRepository;
import com.epam.jwd.core_final.repository.SpaceshipsRepository;
import com.epam.jwd.core_final.repository.impl.CrewRepositoryImpl;
import com.epam.jwd.core_final.repository.impl.PlanetRepositoryImpl;
import com.epam.jwd.core_final.repository.impl.SpaceshipsRepositoryImpl;
import com.epam.jwd.core_final.util.PropertyReaderUtil;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

public class NassaContext implements ApplicationContext {
    private final Logger logger = LoggerFactory.getLogger(NassaContext.class);
    @Getter
    private static final ApplicationProperties applicationProperties = new ApplicationProperties(PropertyReaderUtil.getProperties());

    // no getters/setters for them
    private Set<CrewMember> crewMembers = new HashSet<>();
    private final CrewRepository crewRepository = CrewRepositoryImpl.getInstance();

    private Set<Spaceship> spaceships = new HashSet<>();
    private final SpaceshipsRepository spaceshipsRepository = SpaceshipsRepositoryImpl.getInstance();

    private Set<Planet> planetMap = new HashSet<>();
    private final PlanetRepository planetRepository = PlanetRepositoryImpl.getInstance();

    @Override
    @SuppressWarnings("unchecked")
    public <T extends BaseEntity> Set<T> retrieveBaseEntityList(Class<T> tClass) {
        Set<T> collection = null;
        if (tClass == CrewMember.class) {
            collection = (Set<T>) crewMembers;
        } else if (tClass == Spaceship.class) {
            collection = (Set<T>) spaceships;
        } else if (tClass == Planet.class) {
            collection = (Set<T>) planetMap;
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
            crewMembers = (Set<CrewMember>) crewRepository.findAll();
            spaceships = (Set<Spaceship>) spaceshipsRepository.findAll();
            planetMap = (Set<Planet>) planetRepository.findAll();
            logger.info("All cashes were initialized");
        } catch (Exception e) {

            throw new InvalidStateException();
        }
    }

    @Override
    public void emptyAllCash() {
        emptyCash(CrewMember.class);
        emptyCash(Spaceship.class);
        emptyCash(Planet.class);
        logger.info("All cashes were deleted");
    }

    @Override
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
