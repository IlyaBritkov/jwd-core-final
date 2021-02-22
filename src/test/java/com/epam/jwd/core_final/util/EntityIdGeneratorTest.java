package com.epam.jwd.core_final.util;

import com.epam.jwd.core_final.domain.BaseEntity;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.exception.UnknownEntityException;
import org.junit.Assert;
import org.junit.Test;

public class EntityIdGeneratorTest {

    @Test
    public void getNextIdMethodShouldGenerateAppropriateSequentialIdForCrewMemberEntity() {
        Long crewMemberId = EntityIdGenerator.getNextId(CrewMember.class);
        Assert.assertEquals((Long) 0L, crewMemberId);

        crewMemberId = EntityIdGenerator.getNextId(CrewMember.class);
        Assert.assertEquals((Long) 1L, crewMemberId);

        crewMemberId = EntityIdGenerator.getNextId(CrewMember.class);
        Assert.assertEquals((Long) 2L, crewMemberId);
    }

    @Test
    public void getNextIdMethodShouldGenerateAppropriateSequentialIdForFlightMissionEntity() {
        Long flightMissionId = EntityIdGenerator.getNextId(FlightMission.class);
        Assert.assertEquals((Long) 0L, flightMissionId);

        flightMissionId = EntityIdGenerator.getNextId(FlightMission.class);
        Assert.assertEquals((Long) 1L, flightMissionId);

        flightMissionId = EntityIdGenerator.getNextId(FlightMission.class);
        Assert.assertEquals((Long) 2L, flightMissionId);
    }

    @Test
    public void getNextIdMethodShouldGenerateAppropriateSequentialIdForSpaceshipEntity() {
        Long spaceshipId = EntityIdGenerator.getNextId(Spaceship.class);
        Assert.assertEquals((Long) 0L, spaceshipId);

        spaceshipId = EntityIdGenerator.getNextId(Spaceship.class);
        Assert.assertEquals((Long) 1L, spaceshipId);

        spaceshipId = EntityIdGenerator.getNextId(Spaceship.class);
        Assert.assertEquals((Long) 2L, spaceshipId);
    }

    @Test(expected = UnknownEntityException.class)
    public void getNextIdMethodShouldThrowUnknownEntityException() {
        EntityIdGenerator.getNextId(BaseEntity.class);
    }

}