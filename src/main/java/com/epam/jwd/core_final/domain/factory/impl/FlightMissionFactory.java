package com.epam.jwd.core_final.domain.factory.impl;

import com.epam.jwd.core_final.domain.factory.EntityFactory;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;

/**
 * Singleton
 **/
public class FlightMissionFactory implements EntityFactory<FlightMission> {
    private static FlightMissionFactory INSTANCE;

    private FlightMissionFactory() {
    }

    public FlightMission create(@NotNull String name, @NotNull String missionName, @NotNull LocalDate startDate, @NotNull Long distance, Planet from, Planet to) {
        return create(new Object[]{name, missionName, startDate, distance, from, to});
    }

    @Override
    public FlightMission create(Object... args) {
        return new FlightMission((String) args[0], (String) args[1], (LocalDate) args[2], (Long) args[3], (Planet) args[4], (Planet) args[5]);
    }

    public static FlightMissionFactory getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new FlightMissionFactory();
        }
        return INSTANCE;
    }
}
