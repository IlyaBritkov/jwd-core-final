package com.epam.jwd.core_final.domain.factory.impl;

import com.epam.jwd.core_final.domain.Rank;
import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.domain.factory.EntityFactory;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Singleton
 **/
// do the same for other entities
public class CrewMemberFactory implements EntityFactory<CrewMember> {
    private final Logger logger = LoggerFactory.getLogger(CrewMemberFactory.class);
    private static CrewMemberFactory INSTANCE;

    private CrewMemberFactory() {
    }

    public CrewMember create(@NotNull Role role, @NotNull String name, @NotNull Rank rank) {
        return create(new Object[]{role, name, rank});
    }

    @Override
    public CrewMember create(@NotNull Object... args) {
        CrewMember crewMember = new CrewMember((Role) args[0], (String) args[1], (Rank) args[2]);
        logger.trace("New CrewMember was created: {}", crewMember);
        return crewMember;
    }

    public static CrewMemberFactory getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CrewMemberFactory();
        }
        return INSTANCE;
    }

}
