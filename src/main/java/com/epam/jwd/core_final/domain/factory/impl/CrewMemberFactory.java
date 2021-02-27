package com.epam.jwd.core_final.domain.factory.impl;

import com.epam.jwd.core_final.domain.Rank;
import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.domain.factory.EntityFactory;
import org.jetbrains.annotations.NotNull;


/**
 * Singleton
 **/
// do the same for other entities
public class CrewMemberFactory implements EntityFactory<CrewMember> {
    private static CrewMemberFactory INSTANCE;

    private CrewMemberFactory() {
    }

    public CrewMember create(@NotNull String name, @NotNull Role role, @NotNull Rank rank) {
        return create(new Object[]{name, role, rank});
    }

    @Override
    public CrewMember create(@NotNull Object... args) {
        return new CrewMember((String) args[0], (Role) args[1], (Rank) args[2]);
    }

    public static CrewMemberFactory getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CrewMemberFactory();
        }
        return INSTANCE;
    }

}
