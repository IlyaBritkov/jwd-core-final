package com.epam.jwd.core_final.domain.factory.impl;

import com.epam.jwd.core_final.domain.Rank;
import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.util.EntityIdGenerator;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

/**
 * Expected fields:
 * <p>
 * role {@link Role} - member role
 * rank {@link Rank} - member rank
 * isReadyForNextMissions {@link Boolean} - true by default. Set to false, after first failed mission
 */

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@ToString(callSuper = true)
public class CrewMember extends AbstractBaseEntity {
    @NotNull
    @EqualsAndHashCode.Include
    private Role role;
    @NotNull
    @EqualsAndHashCode.Include
    private Rank rank;
    private Boolean isReadyForNextMissions = true;

    protected CrewMember(@NotNull Role role, @NotNull String name, @NotNull Rank rank) {
        super(EntityIdGenerator.getNextId(CrewMember.class), name);
        this.role = role;
        this.rank = rank;
    }



}
