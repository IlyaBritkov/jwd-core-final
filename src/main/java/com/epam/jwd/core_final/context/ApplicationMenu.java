package com.epam.jwd.core_final.context;

import com.epam.jwd.core_final.user_interface.CrewMemberUserInterface;
import com.epam.jwd.core_final.user_interface.SpaceshipUserInterface;

import java.util.Scanner;

// replace Object with your own types
@FunctionalInterface
public interface ApplicationMenu {
    ApplicationContext getApplicationContext();

    static Void printAvailableOptions() {
        System.out.println("Press 1 - work with CrewMembers");
        System.out.println("Press 2 - work with Spaceships");
        System.out.println("Press 3 - work with FlightMissions");
        System.out.println("Press 0 - exit");
        Scanner scanner = new Scanner(System.in);
        handleUserInput(scanner);
        scanner.close();
        return null;
    }

    static Void handleUserInput(Scanner scanner) {
        int action = Integer.parseInt(scanner.nextLine());
        switch (action) {
            case 0:
                break;
            case 1:
                CrewMemberUserInterface.printAvailableOptions(scanner);
                break;
            case 2:
                SpaceshipUserInterface.printAvailableOptions(scanner);
                break;
            case 3:
                break;
        }
        return null;
    }
}
