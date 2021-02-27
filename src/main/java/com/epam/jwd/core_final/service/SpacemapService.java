package com.epam.jwd.core_final.service;

import com.epam.jwd.core_final.domain.factory.impl.Planet;

public interface SpacemapService {

    Planet getRandomPlanet();

    // Dijkstra ?
    int getDistanceBetweenPlanets(Planet first, Planet second);
}

