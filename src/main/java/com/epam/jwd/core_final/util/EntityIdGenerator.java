package com.epam.jwd.core_final.util;

import com.epam.jwd.core_final.domain.BaseEntity;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.exception.UnknownEntityException;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * Class for generation of an id for every entity that implements {@link com.epam.jwd.core_final.domain.BaseEntity} interface.
 **/
public class EntityIdGenerator {
    private static final Logger logger = LoggerFactory.getLogger(EntityIdGenerator.class);

    private static Long crewMemberId = 0L;
    private static Long flightMissionId = 0L;
    private static Long spaceshipId = 0L;

    private EntityIdGenerator() {
    }

    /**
     * Pass the {@link Class} of the entity you want to receive id
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
        }
        return id.orElseThrow(() -> new UnknownEntityException(clazz.getSimpleName()));
    }

}
