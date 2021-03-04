package com.epam.jwd.core_final.repository;

import com.epam.jwd.core_final.domain.factory.impl.Spaceship;

import java.io.IOException;
import java.util.Collection;

public interface SpaceshipsRepository {
    Collection<Spaceship> findAll() throws IOException;

    Spaceship createSpaceship(Spaceship spaceship) throws RuntimeException;

    Spaceship deleteSpaceship(Spaceship spaceship);

}
