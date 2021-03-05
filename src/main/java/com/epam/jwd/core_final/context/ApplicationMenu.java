package com.epam.jwd.core_final.context;

import com.epam.jwd.core_final.user_interface.CrewMemberUserInterface;

import java.util.Scanner;

// todo replace Object with your own types
@FunctionalInterface
public interface ApplicationMenu {
    ApplicationContext getApplicationContext();

    static Void printAvailableOptions() {
        System.out.println("Press 1 - work with CrewMembers");
        System.out.println("Press 2 - work with Spaceships");
        System.out.println("Press 3 - work with FlightMissions");
        System.out.println("Press 0 - exit");
        Scanner scanner = new Scanner(System.in);
        int action = scanner.nextInt();
        scanner.close();
        handleUserInput(action);
        return null;
    }

    static Void handleUserInput(Integer o) {
        switch (o) {
            case 0:
                break;
            case 1:
                CrewMemberUserInterface.printAvailableOptions();
                break;
            case 2:
                break;
            case 3:
                break;
        }
        return null;
    }
}
