package com.epam.jwd.core_final.domain;

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
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Spaceship extends AbstractBaseEntity {
    @NotNull
    private Map<Role, Short> crew;
    @NotNull
    private Long flightDistance;
    // todo: when is defined?
    @EqualsAndHashCode.Exclude
    private Boolean isReadyForNextMissions;

    public Spaceship(@NotNull String name, @NotNull Map<Role, Short> crew, @NotNull Long flightDistance) {
        super(EntityIdGenerator.getNextId(Spaceship.class), name);
        this.crew = crew;
        this.flightDistance = flightDistance;
        this.isReadyForNextMissions = true;
    }
}
