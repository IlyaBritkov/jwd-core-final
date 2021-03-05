package com.epam.jwd.core_final.context.impl;

import com.epam.jwd.core_final.context.ApplicationContext;
import com.epam.jwd.core_final.domain.ApplicationProperties;
import com.epam.jwd.core_final.domain.CashedEntity;
import com.epam.jwd.core_final.domain.factory.impl.*;
import com.epam.jwd.core_final.exception.InvalidStateException;
import com.epam.jwd.core_final.exception.UnknownEntityException;
import com.epam.jwd.core_final.repository.CrewRepository;
import com.epam.jwd.core_final.repository.PlanetRepository;
import com.epam.jwd.core_final.repository.SpaceshipsRepository;
import com.epam.jwd.core_final.repository.impl.CrewRepositoryImpl;
import com.epam.jwd.core_final.repository.impl.PlanetRepositoryImpl;
import com.epam.jwd.core_final.repository.impl.SpaceshipsRepositoryImpl;
import com.epam.jwd.core_final.util.PropertyReaderUtil;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import java.util.stream.Collectors;

public class NassaContext implements ApplicationContext {
    private final Logger logger = LoggerFactory.getLogger(NassaContext.class);
    private static final ApplicationProperties applicationProperties = new ApplicationProperties(PropertyReaderUtil.getProperties());

    @Getter(AccessLevel.PRIVATE)
    @Setter(AccessLevel.PRIVATE)
    private boolean isCrewMembersCashValid = true;

    @Getter(AccessLevel.PRIVATE)
    @Setter(AccessLevel.PRIVATE)
    private boolean isSpaceshipsCashValid = true;

    // no getters/setters for them
    private Set<CashedEntity<CrewMember>> crewMembers = new HashSet<>();
    private final CrewRepository crewRepository = CrewRepositoryImpl.getInstance();

    private Set<CashedEntity<Spaceship>> spaceships = new HashSet<>();
    private final SpaceshipsRepository spaceshipsRepository = SpaceshipsRepositoryImpl.getInstance();

    private Set<CashedEntity<Planet>> planetMap = new HashSet<>();
    private final PlanetRepository planetRepository = PlanetRepositoryImpl.getInstance();

    private Set<CashedEntity<FlightMission>> flightMissions = new HashSet<>();

    public static ApplicationProperties getApplicationProperties() {
        return NassaContext.applicationProperties;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends BaseEntity> Set<CashedEntity<T>> retrieveBaseEntityList(Class<T> tClass) {
        Set<?> collection = null;
        if (tClass == CrewMember.class) {
            collection = crewMembers;
        } else if (tClass == Spaceship.class) {
            collection = spaceships;
        } else if (tClass == Planet.class) {
            collection = planetMap;
        } else if (tClass == FlightMission.class) {
            collection = flightMissions;
        }
        logger.debug("Collection with {} entities was retrieved", tClass.getSimpleName());
        return (Set<CashedEntity<T>>) collection;
    }

    /**
     * You have to read input files, populate collections
     *
     * @throws InvalidStateException
     */

    @Override
    public void init() throws InvalidStateException {
        try {
            refreshAllCash();
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
        emptyCash(FlightMission.class);
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
        } else if (tClass == FlightMission.class) {
            flightMissions = new HashSet<>();
        } else {
            throw new UnknownEntityException(tClass.getSimpleName());
        }
        logger.info("Cash of {} was deleted", tClass.getSimpleName());
    }

    @Override
    public <T extends BaseEntity> T addToCash(T entity) {
        Class<?> tClass = entity.getClass();
        if (tClass == FlightMission.class) {
            flightMissions.add(new CashedEntity<>((FlightMission) entity));
        } else {
            throw new UnknownEntityException(entity.getClass().getSimpleName(), entity);
        }
        return entity;
    }

    @Override
    public <T extends BaseEntity> void deleteFromCash(T entity) {
        AtomicBoolean isDeleted = new AtomicBoolean(false);
        Class<?> tClass = entity.getClass();
        if (tClass == CrewMember.class) {
            crewMembers.forEach(e -> {
                if (e.getEntity().equals(entity)) {
                    e.setValid(false);
                    isDeleted.set(true);
                }
            });
        } else if (tClass == Spaceship.class) {
            spaceships.forEach(e -> {
                if (e.getEntity().equals(entity)) {
                    e.setValid(false);
                    isDeleted.set(true);
                }
            });
        } else if (tClass == FlightMission.class) {
            isDeleted.set(flightMissions.removeIf(e -> e.getEntity().equals(entity)));
        } else {
            throw new UnknownEntityException(tClass.getSimpleName(), entity);
        }

        if (isDeleted.get()) {
            logger.debug("{} was deleted from cash", entity);
        } else {
            logger.debug("{} wasn't deleted from cash because cash hasn't it", entity);
        }

    }

    @Override
    public void refreshAllCash() {
        refreshCash(CrewMember.class);
        refreshCash(Spaceship.class);
        refreshCash(Planet.class);
        refreshCash(FlightMission.class);
        logger.info("All cashes were refreshed");
    }

    @Override
    public <T extends BaseEntity> void refreshCash(Class<T> tClass) {
        try {
            if (tClass == CrewMember.class) {
                crewMembers = crewRepository.findAll().stream()
                        .map((Function<CrewMember, CashedEntity<CrewMember>>) CashedEntity::new)
                        .collect(Collectors.toSet());
                setCrewMembersCashValid(true);
            } else if (tClass == Spaceship.class) {
                spaceships = spaceshipsRepository.findAll().stream()
                        .map((Function<Spaceship, CashedEntity<Spaceship>>) CashedEntity::new)
                        .collect(Collectors.toSet());
                setSpaceshipsCashValid(true);
            } else if (tClass == Planet.class) {
                planetMap = planetRepository.findAll().stream()
                        .map((Function<Planet, CashedEntity<Planet>>) CashedEntity::new)
                        .collect(Collectors.toSet());
            } else if (tClass == FlightMission.class) {
                flightMissions = new HashSet<>();
            } else {
                throw new UnknownEntityException(tClass.getSimpleName());
            }
            logger.info("Cash of {} was refreshed", tClass.getSimpleName());
        } catch (IOException ex) {
            logger.error("Exception was thrown: {}", ex.toString());
            ex.printStackTrace();
        }

    }

    @Override
    public <T extends BaseEntity> boolean isCashValid(Class<T> tClass) {
        if (tClass == CrewMember.class) {
            return isCrewMembersCashValid();
        } else if (tClass == Spaceship.class) {
            return isSpaceshipsCashValid();
        } else {
            throw new UnknownEntityException(tClass.getSimpleName());
        }
    }

    @Override
    public <T extends BaseEntity> void setCashValid(Class<T> tClass, boolean isCashValid) {
        if (tClass == CrewMember.class) {
            setCrewMembersCashValid(isCashValid);
        } else if (tClass == Spaceship.class) {
            setSpaceshipsCashValid(isCashValid);
        } else {
            throw new UnknownEntityException(tClass.getSimpleName());
        }
    }
}
