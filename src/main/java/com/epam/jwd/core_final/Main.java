package com.epam.jwd.core_final;

import com.epam.jwd.core_final.context.Application;
import com.epam.jwd.core_final.context.ApplicationContext;
import com.epam.jwd.core_final.context.ApplicationMenu;
import com.epam.jwd.core_final.exception.InvalidStateException;

public class Main {
    private static ApplicationMenu menu;

    public static void main(String[] args) throws InvalidStateException {
        menu = Application.start();
        Application.startRefreshing();
//        ApplicationMenu.printAvailableOptions();

    }

    public static ApplicationContext getApplicationContext() {
        return menu.getApplicationContext();
    }
}