package com.epam.jwd.core_final.user_interface;

import com.epam.jwd.core_final.context.ApplicationMenu;
import com.epam.jwd.core_final.criteria.CrewMemberCriteria;
import com.epam.jwd.core_final.service.CrewService;
import com.epam.jwd.core_final.service.impl.CrewServiceImpl;

import java.util.Scanner;

public class CrewMemberUserInterface {
    private static final CrewService crewService = CrewServiceImpl.getInstance();

    public static void printAvailableOptions() {
        System.out.println("Press 1 - Print all CrewMembers");
        System.out.println("Press 2 - Print all CrewMembers by criteria");
        System.out.println("Press 3 - Print CrewMembers by criteria");
        System.out.println("Press 4 - Delete CrewMember by criteria");
        System.out.println("Press 0 - Go back");

        Scanner scanner = new Scanner(System.in);
        int action = scanner.nextInt();
        scanner.close();
        handleUserInput(action);
    }

    private static void handleUserInput(Integer o) {
        switch (o) {
            case 0:
                ApplicationMenu.printAvailableOptions();
                break;
            case 1:
                crewService.findAllCrewMembers().forEach(System.out::println);
                printAvailableOptions();
                break;
            case 2:
                CrewMemberCriteria criteria = calculateCriteria();
                break;
            case 3:
                break;
        }
    }

    private static CrewMemberCriteria calculateCriteria() {
        System.out.println("Skip, if field is unnecessary");
        try (Scanner scanner = new Scanner(System.in);) {

        } catch (Exception ex) {
            System.out.println("You entered wrong data");
            printAvailableOptions();
        }
        return null;
    }
}
