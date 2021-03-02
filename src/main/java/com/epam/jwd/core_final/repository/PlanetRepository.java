package com.epam.jwd.core_final.repository;

import com.epam.jwd.core_final.domain.factory.impl.Planet;

import java.io.IOException;
import java.util.Collection;

public interface PlanetRepository {
    Collection<Planet> findAll() throws IOException;

}
