package com.epam.jwd.core_final.domain.factory.impl;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.time.LocalDate;

public class FlightMissionFactoryTest {

    @Test
    public void factoryShouldBeSingleton() {
        Assertions.assertSame(FlightMissionFactory.getInstance(), FlightMissionFactory.getInstance());
    }

    @Test
    public void methodCreateShouldReturnNewEntities() {
        FlightMissionFactory factory = FlightMissionFactory.getInstance();
        FlightMission first = factory.create("Spacex", "SpaceX", LocalDate.now(), 1000L);
        FlightMission second = factory.create("NasaDragon", "NasaDragon", LocalDate.now().plusDays(2), 2000L);
        Assertions.assertNotSame(first, second);
        Assertions.assertNotEquals(first, second);
    }

    @Test
    public void methodCreateShouldReturnNewEntityWithAppropriateFields() {
        String name = "SpaceX";
        String missionName = "SpaceX";
        LocalDate startDate = LocalDate.now();
        Long distance = 1000L;

        FlightMissionFactory factory = FlightMissionFactory.getInstance();
        FlightMission flightMission = factory.create(name, missionName, startDate, distance);
        Assertions.assertEquals(name, flightMission.getName());
        Assertions.assertEquals(missionName, flightMission.getMissionName());
        Assertions.assertEquals(startDate, flightMission.getStartDate());
        Assertions.assertEquals(distance, flightMission.getDistance());
    }
}
