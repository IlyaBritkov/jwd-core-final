package com.epam.jwd.core_final.service;

import com.epam.jwd.core_final.criteria.Criteria;
import com.epam.jwd.core_final.domain.factory.impl.CrewMember;
import com.epam.jwd.core_final.domain.factory.impl.FlightMission;
import com.epam.jwd.core_final.domain.factory.impl.Spaceship;
import com.epam.jwd.core_final.exception.FlightMissionFillingException;

import java.util.List;
import java.util.Optional;

public interface MissionService {

    List<FlightMission> findAllMissions();

    List<FlightMission> findAllMissionsByCriteria(Criteria<? extends FlightMission> criteria);

    Optional<FlightMission> findMissionByCriteria(Criteria<? extends FlightMission> criteria);

    FlightMission createMission(FlightMission flightMission);

    FlightMission deleteMission(FlightMission flightMission);

    FlightMission cancelMission(FlightMission flightMission);

    /**
     * Write all missions to output file
     **/
    void writeAllMissions(List<FlightMission> flightMissions);

    /**
     * Write specific mission to output file
     **/
    FlightMission writeMission(FlightMission flightMission);

    Spaceship assignSpaceshipOnMission(FlightMission flightMission, Spaceship spaceship) throws FlightMissionFillingException;

    CrewMember assignCrewMemberOnMission(FlightMission flightMission, CrewMember crewMember) throws FlightMissionFillingException;

    /**
     * Clear output file, delete all entities from the file
     **/
    void clearFile();

    /**
     * Sets Failed status to flightMission,
     * removes spaceship and its crewMembers
     **/
    void crush(FlightMission flightMission);
}