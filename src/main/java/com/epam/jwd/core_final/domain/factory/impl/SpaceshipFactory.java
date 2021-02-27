package com.epam.jwd.core_final.domain.factory.impl;

import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.domain.factory.EntityFactory;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * Singleton
 **/

public class SpaceshipFactory implements EntityFactory<Spaceship> {
    private static SpaceshipFactory INSTANCE;

    public Spaceship create(@NotNull String name, @NotNull Map<Role, Short> crew, @NotNull Long flightDistance) {
        return create(new Object[]{name, crew, flightDistance});
    }

    @Override
    @SuppressWarnings("unchecked")
    public Spaceship create(Object... args) {
        return new Spaceship((String) args[0], (Map<Role, Short>) args[1], (Long) args[2]);
    }

    public static SpaceshipFactory getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SpaceshipFactory();
        }
        return INSTANCE;
    }
}
