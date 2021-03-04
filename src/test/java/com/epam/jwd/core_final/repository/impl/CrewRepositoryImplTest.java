package com.epam.jwd.core_final.repository.impl;

import com.epam.jwd.core_final.Main;
import com.epam.jwd.core_final.context.ApplicationContext;
import com.epam.jwd.core_final.domain.Rank;
import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.domain.factory.impl.CrewMember;
import com.epam.jwd.core_final.domain.factory.impl.CrewMemberFactory;
import com.epam.jwd.core_final.exception.InvalidStateException;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;

public class CrewRepositoryImplTest {
    private final ApplicationContext applicationContext = Main.getApplicationContext();
    private final CrewRepositoryImpl crewRepository = CrewRepositoryImpl.getInstance();
    private final CrewMemberFactory crewMemberFactory = CrewMemberFactory.getInstance();

    static {
        try {
            Main.main(null);
        } catch (InvalidStateException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void crewRepositoryShouldBeSingleton() {
        Assertions.assertSame(CrewRepositoryImpl.getInstance(), CrewRepositoryImpl.getInstance());
    }

    @Test
    public void methodCreateCrewMemberShouldCreateNewCrewMemberInFile() throws InvalidStateException {
        applicationContext.emptyAllCash();
        applicationContext.init();

        int expectedSize = applicationContext.retrieveBaseEntityList(CrewMember.class).size() + 1;
        final CrewMember crewMember = crewMemberFactory.create(Role.COMMANDER, "Ilya Britkov", Rank.CAPTAIN); // delete this entity at the end of test
        crewRepository.createCrewMember(crewMember);

        applicationContext.emptyAllCash();
        applicationContext.init();

        int actualSize = applicationContext.retrieveBaseEntityList(CrewMember.class).size();

        Assertions.assertEquals(expectedSize, actualSize);

        crewRepository.deleteCrewMember(crewMember);
    }

    @Test
    public void methodDeleteCrewShouldDeleteCrewMemberFromFile() throws InvalidStateException, IOException {
        applicationContext.emptyAllCash();
        applicationContext.init();

        int expectedSize = applicationContext.retrieveBaseEntityList(CrewMember.class).size();
        final CrewMember crewMember = crewMemberFactory.create(Role.COMMANDER, "Ilya Britkov", Rank.CAPTAIN); // delete this entity at the end of test
        crewRepository.createCrewMember(crewMember);

        crewRepository.deleteCrewMember(crewMember);

       applicationContext.refreshCash(CrewMember.class);

        int actualSize = applicationContext.retrieveBaseEntityList(CrewMember.class).size();

        Assertions.assertEquals(expectedSize, actualSize);
    }
}
