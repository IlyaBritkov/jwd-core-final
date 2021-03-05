package com.epam.jwd.core_final.repository;

import com.epam.jwd.core_final.domain.factory.impl.FlightMission;

import java.util.List;

public interface MissionRepository {

    void writeAllMissions(List<FlightMission> flightMissions);


    FlightMission writeMission(FlightMission flightMission);

    /**
     * Clear output file, delete all entities from the file
     **/
    void clear();
}
