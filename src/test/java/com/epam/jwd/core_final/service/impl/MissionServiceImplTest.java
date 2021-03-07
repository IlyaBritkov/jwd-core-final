package com.epam.jwd.core_final.service.impl;

import com.epam.jwd.core_final.Main;
import com.epam.jwd.core_final.context.ApplicationContext;
import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.criteria.FlightMissionCriteria;
import com.epam.jwd.core_final.domain.ApplicationProperties;
import com.epam.jwd.core_final.domain.factory.impl.*;
import com.epam.jwd.core_final.exception.FlightMissionFillingException;
import com.epam.jwd.core_final.exception.InvalidStateException;
import com.epam.jwd.core_final.service.CrewService;
import com.epam.jwd.core_final.service.MissionService;
import com.epam.jwd.core_final.service.SpacemapService;
import com.epam.jwd.core_final.service.SpaceshipService;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class MissionServiceImplTest {
    private final ApplicationContext context = Main.getApplicationContext();
    final ApplicationProperties properties = NassaContext.getApplicationProperties();
    final String filePath = properties.getOutputRootDir() + File.separator + properties.getMissionsFileName();

    private final MissionService missionService = MissionServiceImpl.getInstance();
    private final SpacemapService spacemapService = SpacemapServiceImpl.getInstance();
    private final SpaceshipService spaceshipService = SpaceshipServiceImpl.getInstance();
    private final CrewService crewService = CrewServiceImpl.getInstance();

    private final FlightMissionFactory missionFactory = FlightMissionFactory.getInstance();
    private final PlanetFactory planetFactory = PlanetFactory.getInstance();
    private final SpaceshipFactory spaceshipFactory = SpaceshipFactory.getInstance();

    static {
        try {
            Main.main(null);
        } catch (InvalidStateException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void missionServiceImplShouldBeSingleton() {
        Assertions.assertSame(MissionServiceImpl.getInstance(), MissionServiceImpl.getInstance());
    }

    @Test
    public void methodFindAllMissionsShouldReturnAllFlightMissionsFromCash() throws FlightMissionFillingException {
        context.refreshCash(FlightMission.class);

        final FlightMission firstFlightMission = missionFactory.create("Deadline", "Deadline",
                LocalDateTime.now(), planetFactory.create("Earth", 0, 0),
                planetFactory.create("Mars", 5, 5));

        final FlightMission secondFlightMission = missionFactory.create("Dragon Ball", "Dragon Ball",
                LocalDateTime.now().plusSeconds(59), planetFactory.create("Earth", 0, 0),
                planetFactory.create("Black Hole", 13, 70));


        missionService.createMission(firstFlightMission);
        missionService.createMission(secondFlightMission);

        int expectedValue = 2;
        int actualValue = missionService.findAllMissions().size();

        Assertions.assertEquals(expectedValue, actualValue);
    }

    @Test
    public void methodFindAllMissionsByCriteriaShouldReturnAllMissionBySpecifiedCriteria() throws FlightMissionFillingException {
        context.refreshCash(FlightMission.class);

        final FlightMission firstFlightMission = missionFactory.create("Deadline", "Deadline",
                LocalDateTime.now(), planetFactory.create("Jupiter", 80, 80),
                planetFactory.create("Mars", 5, 5));


        final Planet planetFrom = planetFactory.create("Earth", 0, 0);

        final FlightMission secondFlightMission = missionFactory.create("Dragon Ball", "Dragon Ball",
                LocalDateTime.now().plusSeconds(59), planetFrom,
                planetFactory.create("Black Hole", 13, 70));

        final FlightMission thirdFlightMission = missionFactory.create("SpaceX", "SpaceX",
                LocalDateTime.now().plusDays(1), planetFrom,
                planetFactory.create("Mars", 5, 5));

        final FlightMissionCriteria criteria = FlightMissionCriteria.builder().setFrom(planetFrom).build();

        missionService.createMission(firstFlightMission);
        missionService.createMission(secondFlightMission);
        missionService.createMission(thirdFlightMission);


        int expectedValue = 2;
        int actualValue = missionService.findAllMissionsByCriteria(criteria).size();

        Assertions.assertEquals(expectedValue, actualValue);
    }

    @Test
    public void methodFindMissionByCriteriaShouldReturnOneMission() throws FlightMissionFillingException {
        context.refreshCash(FlightMission.class);

        final FlightMission firstFlightMission = missionFactory.create("Deadline", "Deadline",
                LocalDateTime.now(), planetFactory.create("Jupiter", 80, 80),
                planetFactory.create("Mars", 5, 5));


        final Planet planetFrom = planetFactory.create("Earth", 0, 0);

        final FlightMission secondFlightMission = missionFactory.create("Dragon Ball", "Dragon Ball",
                LocalDateTime.now().plusSeconds(59), planetFrom,
                planetFactory.create("Black Hole", 13, 70));

        final FlightMission thirdFlightMission = missionFactory.create("SpaceX", "SpaceX",
                LocalDateTime.now().plusDays(1), planetFrom,
                planetFactory.create("Mars", 5, 5));

        final FlightMissionCriteria criteria = FlightMissionCriteria.builder().setFrom(planetFrom).build();

        missionService.createMission(firstFlightMission);
        missionService.createMission(secondFlightMission);
        missionService.createMission(thirdFlightMission);

        // if flightMission is empty, an exception will be thrown
        FlightMission flightMission = missionService.findMissionByCriteria(criteria).orElseThrow();
    }

    @Test
    public void methodCreateMissionShouldCreateNewMissionInCash() throws FlightMissionFillingException {
        context.refreshCash(FlightMission.class);

        final FlightMission firstFlightMission = missionFactory.create("Deadline", "Deadline",
                LocalDateTime.now(), planetFactory.create("Jupiter", 80, 80),
                planetFactory.create("Mars", 5, 5));

        int expectedValue = context.retrieveBaseEntityList(FlightMission.class).size() + 1;

        missionService.createMission(firstFlightMission);

        int actualValue = context.retrieveBaseEntityList(FlightMission.class).size();

        Assertions.assertEquals(expectedValue, actualValue);
    }

    @Test
    public void methodDeleteMissionShouldDeleteMissionFromCash() throws FlightMissionFillingException {
        context.refreshCash(FlightMission.class);

        final FlightMission firstFlightMission = missionFactory.create("Deadline", "Deadline",
                LocalDateTime.now(), planetFactory.create("Jupiter", 80, 80),
                planetFactory.create("Mars", 5, 5));
        missionService.createMission(firstFlightMission);

        int expectedValue = context.retrieveBaseEntityList(FlightMission.class).size() - 1;

        missionService.deleteMission(firstFlightMission);
        int actualValue = context.retrieveBaseEntityList(FlightMission.class).size();

        Assertions.assertEquals(expectedValue, actualValue);
    }

    @Test
    public void methodWriteMissionShouldWriteMissionToOutputFile() throws URISyntaxException, IOException, FlightMissionFillingException {
        context.refreshCash(FlightMission.class);
        missionService.clearFile();

        final FlightMission firstFlightMission = missionFactory.create("Deadline", "Deadline",
                LocalDateTime.now(), planetFactory.create("Jupiter", 80, 80),
                planetFactory.create("Mars", 5, 5));

        File file = new File(Objects.requireNonNull(
                MissionServiceImplTest.class.getClassLoader().getResource(filePath)).toURI());

        missionService.writeMissionToFile(firstFlightMission);

        List<String> lines = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            while (bufferedReader.ready()) {
                lines.add(bufferedReader.readLine().trim());
            }
        }

        if (lines.size() == 0) {
            Assertions.fail();
        }
    }

    @Test
    public void methodWriteAllMissionsShouldWriteAllMissionToOutputFile() throws URISyntaxException, IOException, FlightMissionFillingException {
        context.refreshCash(FlightMission.class);
        missionService.clearFile();

        final FlightMission flightMission = missionFactory.create("Deadline", "Deadline",
                LocalDateTime.now(), spacemapService.getRandomPlanet(), spacemapService.getRandomPlanet());

        missionService.writeMissionToFile(flightMission);

        File file = new File(Objects.requireNonNull(
                MissionServiceImplTest.class.getClassLoader().getResource(filePath)).toURI());

        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            while (bufferedReader.ready()) {
                stringBuilder.append(bufferedReader.readLine().trim());
            }
        }

        int oneMissionSize = stringBuilder.toString().length();

        missionService.clearFile();

        missionService.writeAllMissionsToFile(Arrays.asList(flightMission, flightMission));

        stringBuilder = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            while (bufferedReader.ready()) {
                stringBuilder.append(bufferedReader.readLine().trim());
            }
        }

        int expectedSize = oneMissionSize * 2;
        int actualSize = stringBuilder.toString().length();

        Assertions.assertEquals(expectedSize, actualSize);
    }

    @Test
    public void methodClearShouldClearOutputFile() throws URISyntaxException, IOException, FlightMissionFillingException {
        context.refreshCash(FlightMission.class);

        final FlightMission firstFlightMission = missionFactory.create("Deadline", "Deadline",
                LocalDateTime.now(), spacemapService.getRandomPlanet(), spacemapService.getRandomPlanet());

        final FlightMission secondFlightMission = missionFactory.create("DragonBall", "DragonBall",
                LocalDateTime.now().plusSeconds(45), spacemapService.getRandomPlanet(), spacemapService.getRandomPlanet());

        missionService.writeAllMissionsToFile(Arrays.asList(firstFlightMission, secondFlightMission));

        missionService.clearFile();

        File file = new File(Objects.requireNonNull(
                MissionServiceImplTest.class.getClassLoader().getResource(filePath)).toURI());

        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            while (bufferedReader.ready()) {
                stringBuilder.append(bufferedReader.readLine().trim());
            }
        }

        int expectedSize = 0;
        int actualSize = stringBuilder.toString().length();

        Assertions.assertEquals(expectedSize, actualSize);
    }

    @Test
    public void methodAssignSpaceshipOnMissionShouldAssignNewSpaceship() throws FlightMissionFillingException {
        context.refreshCash(Spaceship.class);

        final Planet planetFrom = spacemapService.getRandomPlanet();
        final Planet planetTo = spacemapService.getRandomPlanet();

        FlightMission flightMission = missionFactory.create("DeadLine", "DeadLine", LocalDateTime.now(), planetFrom, planetTo);
        final Spaceship spaceship = spaceshipService.findAllSpaceships().get(0);

        missionService.assignSpaceshipOnMission(flightMission, spaceship);
    }

    @Test(expected = FlightMissionFillingException.class)
    public void methodAssignSpaceshipOnMissionShouldThrowException() throws FlightMissionFillingException {
        context.refreshCash(Spaceship.class);

        final Planet planetFrom = spacemapService.getRandomPlanet();
        final Planet planetTo = spacemapService.getRandomPlanet();

        FlightMission flightMission = missionFactory.create("DeadLine", "DeadLine", LocalDateTime.now(), planetFrom, planetTo);
        final Spaceship firstSpaceship = spaceshipService.findAllSpaceships().get(0);

        try {
            missionService.assignSpaceshipOnMission(flightMission, firstSpaceship);
        } catch (FlightMissionFillingException ex) {
            Assertions.fail();
        }

        final Spaceship secondSpaceship = spaceshipService.findAllSpaceships().get(1);

        missionService.assignSpaceshipOnMission(flightMission, secondSpaceship);
    }

    @Test(expected = FlightMissionFillingException.class)
    public void methodAssignSpaceshipOnMissionShouldThrowExceptionBecauseOfSpaceship() throws FlightMissionFillingException {
        context.refreshCash(Spaceship.class);

        final Planet planetFrom = spacemapService.getRandomPlanet();
        final Planet planetTo = spacemapService.getRandomPlanet();

        FlightMission flightMission = missionFactory.create("DeadLine", "DeadLine", LocalDateTime.now(), planetFrom, planetTo);
        final Spaceship spaceship = spaceshipService.findAllSpaceships().get(0);

        try {
            missionService.assignSpaceshipOnMission(flightMission, spaceship);
        } catch (FlightMissionFillingException ex) {
            Assertions.fail();
        }

        // should throw an exception
        missionService.assignSpaceshipOnMission(flightMission, spaceship);
    }

    @Test(expected = FlightMissionFillingException.class)
    public void methodAssignCrewMemberOnMissionShouldThrowExceptionBecauseSpaceshipIsAbsence() throws InvalidStateException, IOException, FlightMissionFillingException {
        context.refreshAllCash();

        final FlightMission flightMission = missionFactory.create("FLightMission", "FlightMission", LocalDateTime.now(), spacemapService.getRandomPlanet(), spacemapService.getRandomPlanet());

        final CrewMember crewMember = crewService.findAllCrewMembers().get(0);

        // should throw an exception
        missionService.assignCrewMemberOnMission(flightMission, crewMember);
    }

    // todo
//    @Test(expected = FlightMissionFillingException.class)
//    public void methodAssignCrewMemberOnMissionShouldThrowExceptionBecauseOfCrewMember() throws InvalidStateException, IOException, FlightMissionFillingException {
//        context.refreshAllCash();
//
//        final Planet planetFrom = spacemapService.getRandomPlanet();
//        final Planet planetTo = spacemapService.getRandomPlanet();
//
//        final Map<Role, Short> crewMap = new HashMap<>();
//        crewMap.put(Role.COMMANDER, (short) 1);
//        crewMap.put(Role.FLIGHT_ENGINEER, (short) 1);
//        crewMap.put(Role.MISSION_SPECIALIST, (short) 1);
//        crewMap.put(Role.PILOT, (short) 1);
//
//        final Spaceship myOwnSpaceship = spaceshipFactory.create("MyOwnSpaceship", 1000L, crewMap);
//        spaceshipService.createSpaceship(myOwnSpaceship);
//
//        final Spaceship spaceship = spaceshipService.findSpaceshipByCriteria(SpaceshipCriteria.builder().setName("MyOwnSpaceship").build()).orElseThrow();
//        spaceshipService.deleteSpaceship(myOwnSpaceship);
//
//        final FlightMission flightMission = missionFactory.create("DeadLine", "DeadLine", LocalDateTime.now(), planetFrom, planetTo);
//
//        context.refreshCash(CrewMember.class);
//        final CrewMember firstCrewMember = crewService.findAllCrewMembers().get(0);
//        final CrewMember secondCrewMember = crewService.findAllCrewMembers().get(1);
//
//        try {
//            missionService.assignSpaceshipOnMission(flightMission, spaceship);
//        } catch (FlightMissionFillingException e) {
//            Assertions.fail();
//        }
//
//        try {
//            missionService.assignCrewMemberOnMission(flightMission, firstCrewMember);
//        } catch (FlightMissionFillingException e) {
//            Assertions.fail();
//        }
//
//        context.refreshCash(CrewMember.class);
//        // should throw an exception
//        missionService.assignCrewMemberOnMission(flightMission, secondCrewMember);
//    }

}
