package com.epam.jwd.core_final.domain.factory.impl;

import com.epam.jwd.core_final.util.EntityIdGenerator;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

/**
 * Expected fields:
 * <p>
 * location {@link java.util.Map} - planet coordinate in the universe
 */

@Getter
@Setter
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@ToString(callSuper = true)
public class Planet extends AbstractBaseEntity {
    @EqualsAndHashCode.Include
    private Location location;

    public Planet(@NotNull String name, @NotNull Integer x, @NotNull Integer y) {
        super(EntityIdGenerator.getNextId(Planet.class), name);
        this.location = new Location(x, y);
    }

    @Getter
    @Setter
    @EqualsAndHashCode
    @ToString
    static class Location {
        private int x;
        private int y;

        protected Location(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

}
