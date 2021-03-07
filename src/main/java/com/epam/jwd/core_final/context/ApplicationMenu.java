package com.epam.jwd.core_final.context;

import com.epam.jwd.core_final.user_interface.CrewMemberUserInterface;
import com.epam.jwd.core_final.user_interface.FlightMissionUserInterface;
import com.epam.jwd.core_final.user_interface.SpaceshipUserInterface;

import java.util.Scanner;

// replace Object with your own types
@FunctionalInterface
public interface ApplicationMenu {
    ApplicationContext getApplicationContext();

    static Void printAvailableOptions() {
        try (Scanner scanner = new Scanner(System.in)) {
            boolean work = true;
            while (work) {
                System.out.println("Press 1 - work with CrewMembers");
                System.out.println("Press 2 - work with Spaceships");
                System.out.println("Press 3 - work with FlightMissions");
                System.out.println("Press 0 - exit");
                work = handleUserInput(scanner);
            }
        }
        return null;
    }

    static Boolean handleUserInput(Scanner scanner) {
        String line = scanner.nextLine().trim();
        if (line.length() == 0) {
            return false;
        }
        int action = Integer.parseInt(line);
        switch (action) {
            case 0:
                System.exit(0);
            case 1:
                CrewMemberUserInterface.printAvailableOptions(scanner);
                break;
            case 2:
                SpaceshipUserInterface.printAvailableOptions(scanner);
                break;
            case 3:
                FlightMissionUserInterface.printAvailableOptions(scanner);
                break;
        }
        return true;
    }
}
