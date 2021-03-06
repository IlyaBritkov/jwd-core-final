package com.epam.jwd.core_final.user_interface;

import com.epam.jwd.core_final.context.ApplicationMenu;
import com.epam.jwd.core_final.criteria.SpaceshipCriteria;
import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.domain.factory.impl.Spaceship;
import com.epam.jwd.core_final.domain.factory.impl.SpaceshipFactory;
import com.epam.jwd.core_final.exception.InvalidStateException;
import com.epam.jwd.core_final.service.SpaceshipService;
import com.epam.jwd.core_final.service.impl.SpaceshipServiceImpl;

import java.util.*;

public class SpaceshipUserInterface implements UserInterface {
    private static final SpaceshipService spaceshipService = SpaceshipServiceImpl.getInstance();
    private static final SpaceshipFactory spaceshipFactory = SpaceshipFactory.getInstance();

    public static void printAvailableOptions(Scanner scanner) {
        System.out.println("Press 1 - Print all Spaceships");
        System.out.println("Press 2 - Print all Spaceships by criteria");
        System.out.println("Press 3 - Print Spaceship by criteria");
        System.out.println("Press 4 - Create Spaceship");
        System.out.println("Press 5 - Delete Spaceship by criteria");
        System.out.println("Press 0 - Go back");

        handleUserInput(scanner);
    }

    private static void handleUserInput(Scanner scanner) {
        int action = Integer.parseInt(scanner.nextLine());
        SpaceshipCriteria criteria = null;
        try {
            switch (action) {
                case 0:
                    ApplicationMenu.printAvailableOptions();
                    break;
                case 1:
                    List<Spaceship> spaceships = spaceshipService.findAllSpaceships();
                    if (spaceships.size() == 0) {
                        System.out.println("There is no one Spaceship yet");
                    } else {
                        spaceships.forEach(System.out::println);
                    }
                    System.out.println();
                    printAvailableOptions(scanner);
                    break;
                case 2:
                    criteria = calculateCriteria(scanner);
                    List<Spaceship> spaceshipsByCriteria = spaceshipService.findAllSpaceshipsByCriteria(criteria);
                    if (spaceshipsByCriteria.size() == 0) {
                        System.out.println("There is no one CrewMember yet");
                    } else {
                        System.out.println("The result of your search by criteria:" + criteria);
                        spaceshipsByCriteria.forEach(System.out::println);
                    }
                    System.out.println();
                    printAvailableOptions(scanner);
                    break;
                case 3:
                    criteria = calculateCriteria(scanner);
                    Optional<Spaceship> optionalSpaceship = spaceshipService.findSpaceshipByCriteria((criteria));
                    System.out.println("The result of your search by criteria:" + criteria);
                    if (optionalSpaceship.isEmpty()) {
                        System.out.println("There is no one Spaceship yet");
                    } else {
                        System.out.println(optionalSpaceship.get());
                    }
                    System.out.println();
                    printAvailableOptions(scanner);
                    break;
                case 4:
                    Spaceship newSpaceship = calculateSpaceship(scanner);
                    spaceshipService.createSpaceship(newSpaceship);
                    System.out.println("New Spaceship is saved: " + newSpaceship);
                    printAvailableOptions(scanner);
                    break;
                case 5:
                    SpaceshipCriteria deleteCriteria = calculateCriteria(scanner);
                    Spaceship deleteSpaceship = spaceshipService.findSpaceshipByCriteria(deleteCriteria).get();
                    spaceshipService.deleteSpaceship(deleteSpaceship);
                    printAvailableOptions(scanner);
                    break;
            }
        } catch (InvalidStateException e) {
            System.out.println("You entered wrong data");
        }

    }

    private static SpaceshipCriteria calculateCriteria(Scanner scanner) {
        System.out.println("Skip, if field is unnecessary");
        try {
            System.out.print("Id: ");
            String inputString = scanner.nextLine().trim();
            Long id = null;
            if (inputString.length() != 0) {
                id = Long.valueOf(inputString);
            }

            System.out.print("Name: ");
            inputString = scanner.nextLine().trim();
            String name = null;
            if (inputString.length() != 0) {
                name = inputString;
            }

            System.out.print("Distance: ");
            inputString = scanner.nextLine().trim();
            Long distance = null;
            if (inputString.length() != 0) {
                distance = Long.valueOf(inputString);
            }

            System.out.print("IsReadyForNextMission: ");
            inputString = scanner.nextLine().trim();
            Boolean isReadyForNextMission = null;
            if (inputString.length() != 0) {
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

    private static Spaceship calculateSpaceship(Scanner scanner) {
        System.out.println("All fields are required for creation!");
        try {
            System.out.print("Name: ");
            String inputString = scanner.nextLine().trim();
            String name = null;
            if (inputString.length() != 0) {
                name = inputString;
            }

            System.out.print("Crew:\n");
            System.out.print(Role.COMMANDER + ": ");
            inputString = scanner.nextLine().trim();
            Short commanderAmount = null;
            if (inputString.length() != 0) {
                commanderAmount = Short.valueOf(inputString);
            }

            System.out.print(Role.FLIGHT_ENGINEER + ": ");
            inputString = scanner.nextLine().trim();
            Short flightEngineerAmount = null;
            if (inputString.length() != 0) {
                flightEngineerAmount = Short.valueOf(inputString);
            }

            System.out.print(Role.PILOT + ": ");
            inputString = scanner.nextLine().trim();
            Short pilotAmount = null;
            if (inputString.length() != 0) {
                pilotAmount = Short.valueOf(inputString);
            }

            System.out.print(Role.MISSION_SPECIALIST + ": ");
            inputString = scanner.nextLine().trim();
            Short missionSpecialistAmount = null;
            if (inputString.length() != 0) {
                missionSpecialistAmount = Short.valueOf(inputString);
            }

            System.out.print("Distance: ");
            inputString = scanner.nextLine().trim();
            Long distance = null;
            if (inputString.length() != 0) {
                distance = Long.valueOf(inputString);
            }

            final Map<Role, Short> crew = new HashMap<>();
            crew.put(Role.COMMANDER, commanderAmount);
            crew.put(Role.FLIGHT_ENGINEER, flightEngineerAmount);
            crew.put(Role.PILOT, pilotAmount);
            crew.put(Role.MISSION_SPECIALIST, missionSpecialistAmount);

            return spaceshipFactory.create(name, distance, crew);
        } catch (Exception ex) {
            System.out.println("You entered wrong data");
            ex.printStackTrace();
            printAvailableOptions(scanner);
        }
        return null;
    }

}
