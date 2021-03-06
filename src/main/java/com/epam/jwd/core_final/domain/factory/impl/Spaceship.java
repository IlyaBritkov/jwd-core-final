package com.epam.jwd.core_final.domain.factory.impl;

import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.util.EntityIdGenerator;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * crew {@link java.util.Map<Role,Short>}
 * flightDistance {@link Long} - total available flight distance
 * isReadyForNextMissions {@link Boolean} - true by default. Set to false, after first failed mission
 */

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@ToString(callSuper = true)
public class Spaceship extends AbstractBaseEntity {
    @NotNull
    private Map<Role, Short> crew;
    @NotNull
    private Long distance; // max flight distance
    private Boolean isReadyForNextMissions = true;

    protected Spaceship(@NotNull String name, @NotNull Long distance, @NotNull Map<Role, Short> crew) {
        super(EntityIdGenerator.getNextId(Spaceship.class), name);
        this.crew = crew;
        this.distance = distance;
    }
}
