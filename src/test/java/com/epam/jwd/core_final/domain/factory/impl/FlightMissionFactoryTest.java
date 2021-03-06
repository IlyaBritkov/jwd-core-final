package com.epam.jwd.core_final.domain.factory.impl;

import com.epam.jwd.core_final.exception.FlightMissionFillingException;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.time.LocalDateTime;

public class FlightMissionFactoryTest {

    @Test
    public void factoryShouldBeSingleton() {
        Assertions.assertSame(FlightMissionFactory.getInstance(), FlightMissionFactory.getInstance());
    }

    @Test
    public void methodCreateShouldReturnNewEntities() throws FlightMissionFillingException {
        FlightMissionFactory factory = FlightMissionFactory.getInstance();
        FlightMission first = factory.create("Spacex", "SpaceX", LocalDateTime.now(),
                 new Planet("Earth", 1000, 1000), new Planet("Jupiter", 5000, 5000));
        FlightMission second = factory.create("NasaDragon", "NasaDragon", LocalDateTime.now().plusDays(2),
                new Planet("Jupiter", 5000, 5000), new Planet("Earth", 1000, 1000));
        Assertions.assertNotSame(first, second);
        Assertions.assertNotEquals(first, second);
    }

    @Test
    public void methodCreateShouldReturnNewEntityWithAppropriateFields() throws FlightMissionFillingException {
        String name = "SpaceX";
        String missionName = "SpaceX";
        LocalDateTime startDate = LocalDateTime.now();
        Planet from = new Planet("Earth", 1000, 1000);
        Planet to = new Planet("Jupiter", 5000, 5000);

        FlightMissionFactory factory = FlightMissionFactory.getInstance();
        FlightMission flightMission = factory.create(name, missionName, startDate, from, to);
        Assertions.assertEquals(name, flightMission.getName());
        Assertions.assertEquals(missionName, flightMission.getMissionName());
        Assertions.assertEquals(startDate, flightMission.getStartDate());
        Assertions.assertEquals(from, flightMission.getFrom());
        Assertions.assertEquals(to, flightMission.getTo());
    }
}
