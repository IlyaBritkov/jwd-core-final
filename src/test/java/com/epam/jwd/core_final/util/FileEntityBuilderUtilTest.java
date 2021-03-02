package com.epam.jwd.core_final.util;

import com.epam.jwd.core_final.domain.factory.impl.CrewMember;
import com.epam.jwd.core_final.domain.factory.impl.Planet;
import com.epam.jwd.core_final.domain.factory.impl.Spaceship;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;
import java.util.Collection;

public class FileEntityBuilderUtilTest {

    @Test
    public void methodGetCollectionFromFileShouldRetrieveAllCrewMemberEntities() throws IOException {
        int expectedEntityAmount = 100;
        FileEntityBuilderUtil<CrewMember> entityBuilder = new FileEntityBuilderUtil<>(CrewMember.class);
        Collection<CrewMember> collection = entityBuilder.getCollectionFromFile("input/crew");
        Assertions.assertEquals(expectedEntityAmount, collection.size());
    }

    @Test
    public void methodGetCollectionFromFileShouldRetrieveAllSpaceshipEntities() throws IOException {
        int expectedEntityAmount = 30;
        FileEntityBuilderUtil<Spaceship> entityBuilder = new FileEntityBuilderUtil<>(Spaceship.class);
        Collection<Spaceship> collection = entityBuilder.getCollectionFromFile("input/spaceships");
        Assertions.assertEquals(expectedEntityAmount, collection.size());
    }

    @Test
    public void methodGetCollectionFromFileShouldRetrieveAllPlanetEntities() throws IOException {
        int expectedEntityAmount = 34;
        FileEntityBuilderUtil<Planet> entityBuilder = new FileEntityBuilderUtil<>(Planet.class);
        Collection<Planet> collection = entityBuilder.getCollectionFromFile("input/spacemap");
        Assertions.assertEquals(expectedEntityAmount, collection.size());
    }
}
