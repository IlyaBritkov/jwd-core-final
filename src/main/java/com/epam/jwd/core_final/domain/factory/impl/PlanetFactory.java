package com.epam.jwd.core_final.domain.factory.impl;

import com.epam.jwd.core_final.domain.factory.EntityFactory;
import org.jetbrains.annotations.NotNull;

/**
 * Singleton
 **/
public class PlanetFactory implements EntityFactory<Planet> {
    private static PlanetFactory INSTANCE;

    private PlanetFactory() {
    }

    public Planet create(@NotNull String name, @NotNull Integer x, @NotNull Integer y) {
        return create(new Object[]{name, x, y});
    }

    @Override
    public Planet create(Object... args) {
        return new Planet((String) args[0], (Integer) args[1], (Integer) args[2]);
    }

    public static PlanetFactory getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PlanetFactory();
        }
        return INSTANCE;
    }
}
