package com.epam.jwd.core_final.service;

import com.epam.jwd.core_final.domain.factory.impl.Planet;

public interface SpacemapService {

    Planet getRandomPlanet();

    static int getDistanceBetweenPlanets(Planet first, Planet second) {
        int x1 = first.getLocation().getX();
        int y1 = first.getLocation().getY();

        int x2 = second.getLocation().getX();
        int y2 = second.getLocation().getY();

        return (int) Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }
}

