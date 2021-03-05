package com.epam.jwd.core_final.util;

import com.epam.jwd.core_final.domain.factory.impl.*;
import com.epam.jwd.core_final.exception.UnknownEntityException;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class for generation of an id for every entity that implements {@link BaseEntity} interface.
 **/
public class EntityIdGenerator {
    private static final Logger logger = LoggerFactory.getLogger(EntityIdGenerator.class);

    @Getter
    private static Long crewMemberId = 1L;
    private static Long flightMissionId = 1L;
    private static Long spaceshipId = 1L;
    private static Long planetId = 1L;

    private EntityIdGenerator() {
    }

    /**
     * Pass the {@link BaseEntity} implementation you want to receive next id
     **/
    public static Long getNextId(@NotNull Class<? extends BaseEntity> clazz) {
        Long id = 0L;
        if (clazz == CrewMember.class) {
            logger.trace("New id for {} = {}", CrewMember.class.getSimpleName(), crewMemberId);
            id = crewMemberId++;
        } else if (clazz == FlightMission.class) {
            logger.trace("New id for {} = {}", FlightMission.class.getSimpleName(), flightMissionId);
            id = flightMissionId++;
        } else if (clazz == Spaceship.class) {
            logger.trace("New id for {} = {}", Spaceship.class.getSimpleName(), spaceshipId);
            id = spaceshipId++;
        } else if (clazz == Planet.class) {
            logger.trace("New id for {} = {}", Planet.class.getSimpleName(), planetId);
            id = planetId++;
        } else {
            throw new UnknownEntityException(clazz.getSimpleName());
        }
        if (id == Long.MAX_VALUE) {
            id = 0L;
        }
        return id;
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
            crewMemberId = 1L;
        } else if (tClass == Spaceship.class) {
            spaceshipId = 1L;
        } else if (tClass == FlightMission.class) {
            flightMissionId = 1L;
        } else if (tClass == Planet.class) {
            planetId = 1L;
        } else {
            throw new UnknownEntityException(tClass.getSimpleName());
        }
        logger.info("Id counter for {} was reset", tClass.getSimpleName());
    }


}
