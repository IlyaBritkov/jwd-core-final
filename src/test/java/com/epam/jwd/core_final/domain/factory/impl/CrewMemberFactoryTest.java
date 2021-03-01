package com.epam.jwd.core_final.domain.factory.impl;

import com.epam.jwd.core_final.domain.Rank;
import com.epam.jwd.core_final.domain.Role;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class CrewMemberFactoryTest {

    @Test
    public void factoryShouldBeSingleton() {
        Assertions.assertSame(CrewMemberFactory.getInstance(), CrewMemberFactory.getInstance());
    }

    @Test
    public void methodCreateShouldReturnNewEntities() {
        CrewMemberFactory factory = CrewMemberFactory.getInstance();
        CrewMember first = factory.create(Role.FLIGHT_ENGINEER, "Ilya", Rank.FIRST_OFFICER);
        CrewMember second = factory.create(Role.COMMANDER, "Dimon", Rank.CAPTAIN);
        Assertions.assertNotSame(first, second);
        Assertions.assertNotEquals(first, second);
    }

    @Test
    public void methodCreateShouldReturnNewEntityWithAppropriateFields() {
        String name = "Ilya";
        Role role = Role.FLIGHT_ENGINEER;
        Rank rank = Rank.FIRST_OFFICER;
        CrewMemberFactory factory = CrewMemberFactory.getInstance();
        CrewMember crewMember = factory.create(Role.FLIGHT_ENGINEER, "Ilya", Rank.FIRST_OFFICER);
        Assertions.assertEquals(name, crewMember.getName());
        Assertions.assertEquals(role, crewMember.getRole());
        Assertions.assertEquals(rank, crewMember.getRank());
    }
}
