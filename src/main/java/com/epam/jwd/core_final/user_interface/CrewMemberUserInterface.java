package com.epam.jwd.core_final.user_interface;

import com.epam.jwd.core_final.context.ApplicationMenu;
import com.epam.jwd.core_final.criteria.CrewMemberCriteria;
import com.epam.jwd.core_final.domain.Rank;
import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.domain.factory.impl.CrewMember;
import com.epam.jwd.core_final.domain.factory.impl.CrewMemberFactory;
import com.epam.jwd.core_final.service.CrewService;
import com.epam.jwd.core_final.service.impl.CrewServiceImpl;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class CrewMemberUserInterface implements UserInterface{
    private static final CrewService crewService = CrewServiceImpl.getInstance();
    private static final CrewMemberFactory crewFactory = CrewMemberFactory.getInstance();

    public static void printAvailableOptions(Scanner scanner) {
        System.out.println("Press 1 - Print all CrewMembers");
        System.out.println("Press 2 - Print all CrewMembers by criteria");
        System.out.println("Press 3 - Print CrewMembers by criteria");
        System.out.println("Press 4 - Create CrewMember");
        System.out.println("Press 5 - Delete CrewMember by criteria");
        System.out.println("Press 0 - Go back");

        handleUserInput(scanner);
    }

    private static void handleUserInput(Scanner scanner) {
        int action = Integer.parseInt(scanner.nextLine());
        CrewMemberCriteria criteria = null;
        switch (action) {
            case 0:
                ApplicationMenu.printAvailableOptions();
                break;
            case 1:
                List<CrewMember> crewMembers = crewService.findAllCrewMembers();
                if (crewMembers.size() == 0) {
                    System.out.println("There is no one CrewMember yet");
                } else {
                    crewMembers.forEach(System.out::println);
                }
                System.out.println();
                printAvailableOptions(scanner);
                break;
            case 2:
                criteria = calculateCriteria(scanner);
                List<CrewMember> crewMembersByCriteria = crewService.findAllCrewMembersByCriteria(criteria);
                if (crewMembersByCriteria.size() == 0) {
                    System.out.println("The result of your search by criteria:" + criteria);
                    System.out.println("There is no one CrewMember yet");
                } else {
                    crewMembersByCriteria.forEach(System.out::println);
                }
                System.out.println();
                printAvailableOptions(scanner);
                break;
            case 3:
                criteria = calculateCriteria(scanner);
                Optional<CrewMember> optionalCrewMember = crewService.findCrewMemberByCriteria((criteria));
                System.out.println("The result of your search by criteria:" + criteria);
                if (optionalCrewMember.isEmpty()) {
                    System.out.println("There is no one CrewMember yet");
                } else {
                    System.out.println(optionalCrewMember.get());
                }
                System.out.println();
                printAvailableOptions(scanner);
                break;
            case 4:
                CrewMember newCrewMember = calculateCrewMember(scanner);
                crewService.createCrewMember(newCrewMember);
                System.out.println("New CrewMember is saved: " + newCrewMember);
                printAvailableOptions(scanner);
                break;
            case 5:
                CrewMemberCriteria deleteCriteria = calculateCriteria(scanner);
                CrewMember deleteCrewMember = crewService.findCrewMemberByCriteria(deleteCriteria).get();
                crewService.deleteCrewMember(deleteCrewMember);
                printAvailableOptions(scanner);
                break;
        }
    }

    private static CrewMemberCriteria calculateCriteria(Scanner scanner) {
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

            System.out.print("Role: ");
            inputString = scanner.nextLine().trim();
            Role role = null;
            if (inputString.length() != 0) {
                role = Role.resolveRoleById(Integer.parseInt(inputString));
            }

            System.out.print("Rank: ");
            inputString = scanner.nextLine().trim();
            Rank rank = null;
            if (inputString.length() != 0) {
                rank = Rank.resolveRankById(Integer.parseInt(inputString));
            }

            System.out.print("IsReadyForNextMission: ");
            inputString = scanner.nextLine().trim();
            Boolean isReadyForNextMission = null;
            if (inputString.length() != 0) {
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

    private static CrewMember calculateCrewMember(Scanner scanner) {
        System.out.println("All fields are required for creation!");
        try {
            System.out.print("Name: ");
            String inputString = scanner.nextLine().trim();
            String name = null;
            if (inputString.length() != 0) {
                name = inputString;
            }

            System.out.print("Role: ");
            inputString = scanner.nextLine().trim();
            Role role = null;
            if (inputString.length() != 0) {
                role = Role.resolveRoleById(Integer.parseInt(inputString));
            }

            System.out.print("Rank: ");
            inputString = scanner.nextLine().trim();
            Rank rank = null;
            if (inputString.length() != 0) {
                rank = Rank.resolveRankById(Integer.parseInt(inputString));
            }
            return crewFactory.create(role, name, rank);
        } catch (Exception ex) {
            System.out.println("You entered wrong data");
            ex.printStackTrace();
            printAvailableOptions(scanner);
        }
        return null;
    }
}
