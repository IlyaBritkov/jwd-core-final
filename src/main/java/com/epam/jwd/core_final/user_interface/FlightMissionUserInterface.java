package com.epam.jwd.core_final.user_interface;

import com.epam.jwd.core_final.context.ApplicationMenu;
import com.epam.jwd.core_final.criteria.CrewMemberCriteria;
import com.epam.jwd.core_final.criteria.FlightMissionCriteria;
import com.epam.jwd.core_final.criteria.SpaceshipCriteria;
import com.epam.jwd.core_final.domain.MissionStatus;
import com.epam.jwd.core_final.domain.Rank;
import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.domain.factory.impl.*;
import com.epam.jwd.core_final.exception.FlightMissionFillingException;
import com.epam.jwd.core_final.service.CrewService;
import com.epam.jwd.core_final.service.MissionService;
import com.epam.jwd.core_final.service.SpacemapService;
import com.epam.jwd.core_final.service.SpaceshipService;
import com.epam.jwd.core_final.service.impl.CrewServiceImpl;
import com.epam.jwd.core_final.service.impl.MissionServiceImpl;
import com.epam.jwd.core_final.service.impl.SpacemapServiceImpl;
import com.epam.jwd.core_final.service.impl.SpaceshipServiceImpl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class FlightMissionUserInterface implements UserInterface {
    private static final MissionService missionService = MissionServiceImpl.getInstance();
    private static final FlightMissionFactory missionFactory = FlightMissionFactory.getInstance();

    private static final SpaceshipService spaceshipService = SpaceshipServiceImpl.getInstance();

    private static final SpacemapService planetService = SpacemapServiceImpl.getInstance();

    private static final CrewService crewService = CrewServiceImpl.getInstance();

    public static void printAvailableOptions(Scanner scanner) {
        while (true) {
            System.out.println("Press 1 - Print all FlightMissions");
            System.out.println("Press 2 - Print all FlightMissions by criteria");
            System.out.println("Press 3 - Print FlightMission by criteria");
            System.out.println("Press 4 - Create FlightMission");
            System.out.println("Press 5 - Delete FlightMission by criteria");
            System.out.println("Press 6 - Cancel FlightMissions by criteria");
            System.out.println("Press 7 - Write all FlightMissions to output file");
            System.out.println("Press 8 - Write FlightMissions by criteria to output file");
            System.out.println("Press 9 - Assign Spaceship by criteria on FlightMission by criteria");
            System.out.println("Press 10 - Assign CrewMember by criteria on FlightMission by criteria");
            System.out.println("Press 11 - Clear output file with all FlightMissions");
            System.out.println("Press 0 - Go back");

            handleUserInput(scanner);
        }
    }

    private static void handleUserInput(Scanner scanner) {
        int action = Integer.parseInt(scanner.nextLine());
        FlightMissionCriteria criteria;
        try {
            switch (action) {
                case 0:
                    ApplicationMenu.printAvailableOptions();
                    break;
                case 1:
                    List<FlightMission> flightMissions = missionService.findAllMissions();
                    if (flightMissions.size() == 0) {
                        System.out.println("There is no one FLightMissions yet");
                    } else {
                        flightMissions.forEach(System.out::println);
                    }
                    System.out.println();
                    printAvailableOptions(scanner);
                    break;
                case 2:
                    System.out.println("Printed FlightMissions by criteria:");
                    criteria = calculateFlightMissionCriteria(scanner);
                    List<FlightMission> flightMissionByCriteria = missionService.findAllMissionsByCriteria(criteria);
                    if (flightMissionByCriteria.size() == 0) {
                        System.out.println("There is no one FlightMission yet");
                    } else {
                        System.out.println("The result of your search by criteria:" + criteria);
                        flightMissionByCriteria.forEach(System.out::println);
                    }
                    System.out.println();
                    printAvailableOptions(scanner);
                    break;
                case 3:
                    System.out.println("Printed FlightMission by criteria:");
                    criteria = calculateFlightMissionCriteria(scanner);
                    Optional<FlightMission> optionalFlightMission = missionService.findMissionByCriteria((criteria));
                    System.out.println("The result of your search by criteria:" + criteria);
                    if (optionalFlightMission.isEmpty()) {
                        System.out.println("There is no one FlightMission yet");
                    } else {
                        System.out.println(optionalFlightMission.get());
                    }
                    System.out.println();
                    printAvailableOptions(scanner);
                    break;
                case 4:
                    FlightMission newFlightMission = calculateFlightMission(scanner);
                    missionService.createMission(newFlightMission);
                    System.out.println("New FlightMission is saved: " + newFlightMission);
                    printAvailableOptions(scanner);
                    break;
                case 5:
                    System.out.println("Deleted FlightMission Criteria:");
                    FlightMissionCriteria deleteCriteria = calculateFlightMissionCriteria(scanner);
                    FlightMission deleteFlightMission = missionService.findMissionByCriteria(deleteCriteria).get();
                    missionService.deleteMission(deleteFlightMission);
                    System.out.println("FlightMission was deleted: " + deleteFlightMission);
                    printAvailableOptions(scanner);
                    break;
                case 6:
                    System.out.println("Canceled FlightMission Criteria:");
                    FlightMissionCriteria cancelCriteria = calculateFlightMissionCriteria(scanner);
                    FlightMission cancelFlightMission = missionService.findMissionByCriteria(cancelCriteria).get();
                    missionService.cancelMission(cancelFlightMission);
                    printAvailableOptions(scanner);
                    break;
                case 7:
                    missionService.writeAllMissionsToFile(missionService.findAllMissions());
                    printAvailableOptions(scanner);
                    break;
                case 8:
                    System.out.println("Written FlightMission Criteria:");
                    FlightMissionCriteria writeFlightMissionCriteria = calculateFlightMissionCriteria(scanner);
                    missionService.writeMissionToFile(missionService.findMissionByCriteria(writeFlightMissionCriteria).get());
                    printAvailableOptions(scanner);
                    break;
                case 9:
                    System.out.println("Assigned FlightMission Criteria:");
                    FlightMissionCriteria assignedFlightMissionCriteria = calculateFlightMissionCriteria(scanner);
                    System.out.println("Assigned Spaceship Criteria:");
                    SpaceshipCriteria assignedSpaceshipCriteria = calculateSpaceshipCriteria(scanner);
                    try {
                        Spaceship assignedSpaceship = spaceshipService.findSpaceshipByCriteria(assignedSpaceshipCriteria).get();
                        FlightMission assignedFlightMission = missionService.findMissionByCriteria(assignedFlightMissionCriteria).get();
                        missionService.assignSpaceshipOnMission(assignedFlightMission, assignedSpaceship);
                    } catch (FlightMissionFillingException e) {
                        System.out.println(e.getMessage());
                    } catch (NullPointerException ex) {
                    }
                    printAvailableOptions(scanner);
                    break;
                case 10:
                    System.out.println("Assigned FlightMission Criteria:");
                    assignedFlightMissionCriteria = calculateFlightMissionCriteria(scanner);
                    System.out.println("Assigned CrewMember Criteria:");
                    CrewMemberCriteria assignedCrewMemberCriteria = calculateCrewMemberCriteria(scanner);
                    try {
                        FlightMission assignedFlightMission = missionService.findMissionByCriteria(assignedFlightMissionCriteria).get();
                        CrewMember assignedCrewMember = crewService.findCrewMemberByCriteria(assignedCrewMemberCriteria).get();
                        missionService.assignCrewMemberOnMission(assignedFlightMission, assignedCrewMember);
                    } catch (FlightMissionFillingException e) {
                        System.out.println(e.getMessage());
                    } catch (NullPointerException ex) {
                    }
                    printAvailableOptions(scanner);
                    break;
                case 11:
                    missionService.clearFile();
                    printAvailableOptions(scanner);
                    break;
            }
        } catch (Exception e) {
            System.out.println("You entered wrong data");
        }
    }

    private static FlightMissionCriteria calculateFlightMissionCriteria(Scanner scanner) {
        System.out.println("Skip, if field is unnecessary");
        try {
            System.out.print("Id: ");
            String inputString = scanner.nextLine().trim();
            Long id = null;
            if (inputString.trim().length() != 0) {
                id = Long.valueOf(inputString);
            }

            System.out.print("Name: ");
            inputString = scanner.nextLine().trim();
            String name = null;
            if (inputString.trim().length() != 0) {
                name = inputString;
            }

            System.out.print("MissionName: ");
            inputString = scanner.nextLine().trim();
            String missionName = null;
            if (inputString.trim().length() != 0) {
                missionName = inputString;
            }

            final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            System.out.println("Date in format: yyyy-MM-dd HH:mm");
            System.out.print("StartDate: ");
            inputString = scanner.nextLine().trim();
            LocalDateTime startDate = null;
            if (inputString.trim().length() != 0) {
                startDate = LocalDateTime.parse(inputString, formatter);
            }

            System.out.print("EndDate: ");
            inputString = scanner.nextLine().trim();
            LocalDateTime endDate = null;
            if (inputString.trim().length() != 0) {
                endDate = LocalDateTime.parse(inputString, formatter);
            }

            System.out.print("Distance: ");
            inputString = scanner.nextLine().trim();
            Long distance = null;
            if (inputString.trim().length() != 0) {
                distance = Long.valueOf(inputString);
            }

            System.out.println("Spaceship: ");
            System.out.println("---------");
            SpaceshipCriteria spaceshipCriteria = calculateSpaceshipCriteria(scanner);
            Spaceship assignedSpaceship = null;
            if (inputString.trim().length() != 0) {
                assignedSpaceship = spaceshipService.findSpaceshipByCriteria(spaceshipCriteria).get();
            }

            System.out.println("_________");
            System.out.print("MissionStatus: ");
            inputString = scanner.nextLine().trim();
            MissionStatus missionStatus = null;
            if (inputString.trim().length() != 0) {
                missionStatus = MissionStatus.resolveMissionStatusById(Integer.parseInt(inputString));
            }

            return FlightMissionCriteria.builder()
                    .setId(id)
                    .setName(name)
                    .setMissionName(missionName)
                    .setStartDate(startDate)
                    .setEndDate(endDate)
                    .setDistance(distance)
                    .setAssignedSpaceship(assignedSpaceship)
                    .setMissionStatus(missionStatus)
                    .build();
        } catch (Exception ex) {
            System.out.println("You entered wrong data");
            ex.printStackTrace();
            printAvailableOptions(scanner);
        }

        return FlightMissionCriteria.builder().build();
    }

    private static SpaceshipCriteria calculateSpaceshipCriteria(Scanner scanner) {
        System.out.println("Skip, if field is unnecessary");
        try {
            System.out.print("Id: ");
            String inputString = scanner.nextLine().trim();
            Long id = null;
            if (inputString.trim().length() != 0) {
                id = Long.valueOf(inputString);
            }

            System.out.print("Name: ");
            inputString = scanner.nextLine().trim();
            String name = null;
            if (inputString.trim().length() != 0) {
                name = inputString;
            }

            System.out.print("Distance: ");
            inputString = scanner.nextLine().trim();
            Long distance = null;
            if (inputString.trim().length() != 0) {
                distance = Long.valueOf(inputString);
            }

            System.out.print("IsReadyForNextMission: ");
            inputString = scanner.nextLine().trim();
            Boolean isReadyForNextMission = null;
            if (inputString.trim().length() != 0) {
                isReadyForNextMission = Boolean.parseBoolean(inputString);
            }

            return SpaceshipCriteria.builder()
                    .setId(id)
                    .setName(name)
                    .setFlightDistance(distance)
                    .setReadyForNextMissions(isReadyForNextMission)
                    .build();
        } catch (Exception ex) {
            System.out.println("You entered wrong data");
            ex.printStackTrace();
            printAvailableOptions(scanner);
        }
        return SpaceshipCriteria.builder().build();
    }

    private static CrewMemberCriteria calculateCrewMemberCriteria(Scanner scanner) {
        System.out.println("Skip, if field is unnecessary");
        try {
            System.out.print("Id: ");
            String inputString = scanner.nextLine().trim();
            Long id = null;
            if (inputString.trim().length() != 0) {
                id = Long.valueOf(inputString);
            }

            System.out.print("Name: ");
            inputString = scanner.nextLine().trim();
            String name = null;
            if (inputString.trim().length() != 0) {
                name = inputString;
            }

            System.out.print("Role: ");
            inputString = scanner.nextLine().trim();
            Role role = null;
            if (inputString.trim().length() != 0) {
                role = Role.resolveRoleById(Integer.parseInt(inputString));
            }

            System.out.print("Rank: ");
            inputString = scanner.nextLine().trim();
            Rank rank = null;
            if (inputString.trim().length() != 0) {
                rank = Rank.resolveRankById(Integer.parseInt(inputString));
            }

            System.out.print("IsReadyForNextMission: ");
            inputString = scanner.nextLine().trim();
            Boolean isReadyForNextMission = null;
            if (inputString.trim().length() != 0) {
                isReadyForNextMission = Boolean.parseBoolean(inputString);
            }

            return CrewMemberCriteria.builder()
                    .setId(id)
                    .setName(name)
                    .setRole(role)
                    .setRank(rank)
                    .setReadyForNextMissions(isReadyForNextMission)
                    .build();
        } catch (Exception ex) {
            System.out.println("You entered wrong data");
            ex.printStackTrace();
            printAvailableOptions(scanner);
        }

        return CrewMemberCriteria.builder().build();
    }


    private static FlightMission calculateFlightMission(Scanner scanner) {
        System.out.println("All fields are required for creation!");
        try {
            System.out.print("Name: ");
            String inputString = scanner.nextLine().trim();
            String name = null;
            if (inputString.trim().length() != 0) {
                name = inputString;
            }

            System.out.print("MissionName: ");
            inputString = scanner.nextLine().trim();
            String missionName = null;
            if (inputString.trim().length() != 0) {
                missionName = inputString;
            }

            final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            System.out.println("Enter date in format: yyyy-MM-dd HH:mm or skip to set now");
            System.out.print("StartDate: ");
            inputString = scanner.nextLine().trim();
            LocalDateTime startDate = null;
            if (inputString.trim().length() != 0) {
                startDate = LocalDateTime.parse(inputString, formatter);
            } else {
                startDate = LocalDateTime.now();
            }

            final Planet from = planetService.getRandomPlanet();

            final Planet to = planetService.getRandomPlanet();

            return missionFactory.create(name, missionName, startDate, from, to);
        } catch (Exception ex) {
            System.out.println("You entered wrong data");
            printAvailableOptions(scanner);
        }
        return null;
    }

}