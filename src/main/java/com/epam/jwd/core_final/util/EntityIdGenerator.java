package com.epam.jwd.core_final.util;

import com.epam.jwd.core_final.domain.factory.impl.*;
import com.epam.jwd.core_final.exception.UnknownEntityException;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * Class for generation of an id for every entity that implements {@link BaseEntity} interface.
 **/
public class EntityIdGenerator {
    private static final Logger logger = LoggerFactory.getLogger(EntityIdGenerator.class);

    private static Long crewMemberId = 0L;
    private static Long flightMissionId = 0L;
    private static Long spaceshipId = 0L;
    private static Long planetId = 0L;

    private EntityIdGenerator() {
    }

    /**
     * Pass the {@link BaseEntity} implementation you want to receive next id
     **/
    public static Long getNextId(@NotNull Class<? extends BaseEntity> clazz) {
        Optional<Long> id = Optional.empty();
        if (clazz == CrewMember.class) {
            logger.trace("New id for {} = {}", CrewMember.class.getSimpleName(), crewMemberId);
            id = Optional.of(crewMemberId++);
        } else if (clazz == FlightMission.class) {
            logger.trace("New id for {} = {}", FlightMission.class.getSimpleName(), flightMissionId);
            id = Optional.of(flightMissionId++);
        } else if (clazz == Spaceship.class) {
            logger.trace("New id for {} = {}", Spaceship.class.getSimpleName(), spaceshipId);
            id = Optional.of(spaceshipId++);
        } else if (clazz == Planet.class) {
            logger.trace("New id for {} = {}", Planet.class.getSimpleName(), planetId);
            id = Optional.of(planetId++);
        }
        return id.orElseThrow(() -> new UnknownEntityException(clazz.getSimpleName()));
    }

    /**
     * Reset all counters for id generation.
     * After invocation of this method all ids will be generated start with 0
     **/
    public static void resetAll() {
        reset(CrewMember.class);
        reset(Spaceship.class);
        reset(FlightMission.class);
        reset(Planet.class);
        logger.info("Id counters for all entities were reset");

    }

    /**
     * Reset id counter for {@param tClass} entity.
     * After invocation of this method ids will be generated start with 0
     **/
    public static <T extends BaseEntity> void reset(Class<T> tClass) {
        if (tClass == CrewMember.class) {
            crewMemberId = 0L;
        } else if (tClass == Spaceship.class) {
            spaceshipId = 0L;
        } else if (tClass == FlightMission.class) {
            flightMissionId = 0L;
        } else if (tClass == Planet.class) {
            planetId = 0L;
        } else {
            throw new UnknownEntityException(tClass.getSimpleName());
        }
        logger.info("Id counter for {} was reset", tClass.getSimpleName());
    }


}
