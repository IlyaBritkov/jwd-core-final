package com.epam.jwd.core_final.context.impl;

import com.epam.jwd.core_final.domain.CashedEntity;
import com.epam.jwd.core_final.domain.factory.impl.CrewMember;
import com.epam.jwd.core_final.domain.factory.impl.Planet;
import com.epam.jwd.core_final.domain.factory.impl.Spaceship;
import com.epam.jwd.core_final.exception.InvalidStateException;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.Collection;
import java.util.stream.Collectors;

public class NassaContextTest {
    private final NassaContext nassaContext = NassaContext.getInstance();

    @Test
    public void methodRetrieveBaseEntityListShouldRetrieveAppropriateCollection() {
        final Collection<CrewMember> crewMembers = nassaContext.retrieveBaseEntityList(CrewMember.class).stream()
                .map(CashedEntity::getEntity)
                .collect(Collectors.toList());
        final Collection<Spaceship> spaceships = nassaContext.retrieveBaseEntityList(Spaceship.class).stream()
                .map(CashedEntity::getEntity)
                .collect(Collectors.toList());
        final Collection<Planet> planets = nassaContext.retrieveBaseEntityList(Planet.class).stream()
                .map(CashedEntity::getEntity)
                .collect(Collectors.toList());
    }

    @Test
    public void methodRetrieveBaseEntityListShouldRetrieveAllCrewMemberEntities() throws InvalidStateException {
        nassaContext.emptyAllCash();
        nassaContext.init();
        int expectedEntityAmount = 100;
        final Collection<CrewMember> collection = nassaContext.retrieveBaseEntityList(CrewMember.class).stream()
                .map(CashedEntity::getEntity)
                .collect(Collectors.toList());
        Assertions.assertEquals(expectedEntityAmount, collection.size());
    }

    @Test
    public void methodRetrieveBaseEntityListShouldRetrieveAllSpaceshipEntities() throws InvalidStateException {
        nassaContext.emptyAllCash();
        nassaContext.init();
        int expectedEntityAmount = 30;
        final Collection<Spaceship> collection = nassaContext.retrieveBaseEntityList(Spaceship.class).stream()
                .map(CashedEntity::getEntity)
                .collect(Collectors.toSet());
        Assertions.assertEquals(expectedEntityAmount, collection.size());
    }

    @Test
    public void methodRetrieveBaseEntityListShouldRetrieveAllPlanetEntities() throws InvalidStateException {
        final NassaContext nassaContext = NassaContext.getInstance();
        nassaContext.emptyAllCash();
        nassaContext.init();
        int expectedEntityAmount = 34;
        final Collection<Planet> collection = nassaContext.retrieveBaseEntityList(Planet.class).stream()
                .map(CashedEntity::getEntity)
                .collect(Collectors.toList());
        Assertions.assertEquals(expectedEntityAmount, collection.size());
    }

}
