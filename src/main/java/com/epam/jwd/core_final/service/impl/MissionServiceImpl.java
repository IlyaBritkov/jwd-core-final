package com.epam.jwd.core_final.service.impl;

import com.epam.jwd.core_final.Main;
import com.epam.jwd.core_final.context.ApplicationContext;
import com.epam.jwd.core_final.criteria.Criteria;
import com.epam.jwd.core_final.criteria.FlightMissionCriteria;
import com.epam.jwd.core_final.domain.CashedEntity;
import com.epam.jwd.core_final.domain.MissionStatus;
import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.domain.factory.impl.CrewMember;
import com.epam.jwd.core_final.domain.factory.impl.FlightMission;
import com.epam.jwd.core_final.domain.factory.impl.Spaceship;
import com.epam.jwd.core_final.exception.FlightMissionFillingException;
import com.epam.jwd.core_final.exception.UnreachableSpaceItemException;
import com.epam.jwd.core_final.repository.MissionRepository;
import com.epam.jwd.core_final.repository.impl.MissionRepositoryImpl;
import com.epam.jwd.core_final.service.CrewService;
import com.epam.jwd.core_final.service.MissionService;
import com.epam.jwd.core_final.service.SpaceshipService;
import com.epam.jwd.core_final.util.ReflectUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Singleton
 **/
public class MissionServiceImpl implements MissionService {
    private final Logger logger = LoggerFactory.getLogger(MissionServiceImpl.class);
    private static MissionServiceImpl INSTANCE;
    private final MissionRepository missionRepository = MissionRepositoryImpl.getInstance();

    private final ApplicationContext context = Main.getApplicationContext();
    private final SpaceshipService spaceshipService = SpaceshipServiceImpl.getInstance();
    private final CrewService crewService = CrewServiceImpl.getInstance();

    private MissionServiceImpl() {
    }

    public static MissionServiceImpl getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MissionServiceImpl();
        }
        return INSTANCE;
    }

    @Override
    public List<FlightMission> findAllMissions() {
        return context.retrieveBaseEntityList(FlightMission.class).stream()
                .map(CashedEntity::getEntity).collect(Collectors.toList());
    }

    @Override
    public List<FlightMission> findAllMissionsByCriteria(Criteria<? extends FlightMission> criteria) {
        List<Field> requiredFieldsList = ReflectUtil.getDeepNotNullFields(FlightMissionCriteria.class, criteria);
        List<Field> entityFieldsList = ReflectUtil.getDeepAllFields(FlightMission.class);

        List<FlightMission> flightMissionList = findAllMissions();
        return flightMissionList
                .stream()
                .filter(entity -> ReflectUtil.compareOnFields(requiredFieldsList, criteria,
                        entityFieldsList, entity))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<FlightMission> findMissionByCriteria(Criteria<? extends FlightMission> criteria) {
        return findAllMissionsByCriteria(criteria)
                .stream()
                .findFirst();
    }

    @Override
    public FlightMission createMission(FlightMission flightMission) {
        return context.addToCash(flightMission);
    }

    @Override
    public FlightMission deleteMission(FlightMission flightMission) {
        context.deleteFromCash(flightMission);
        return flightMission;
    }

    @Override
    public FlightMission cancelMission(FlightMission flightMission) {
        if (flightMission.getMissionStatus() != MissionStatus.COMPLETED &&
                flightMission.getMissionStatus() != MissionStatus.FAILED &&
                flightMission.getMissionStatus() != MissionStatus.IN_PROGRESS) {
            flightMission.setMissionStatus(MissionStatus.CANCELLED);
        }
        return null;
    }

    @Override
    public void writeAllMissionsToFile(List<FlightMission> flightMissions) {
        missionRepository.writeAllMissions(flightMissions);
        logger.info("FlightMissions {} were written to file", flightMissions);
    }

    @Override
    public FlightMission writeMissionToFile(FlightMission flightMission) {
        missionRepository.writeMission(flightMission);
        logger.info("FlightMission {} was written to file", flightMission);
        return flightMission;
    }

    @Override
    // todo maybe replace Runtime exception by checked exception
    public Spaceship assignSpaceshipOnMission(FlightMission flightMission, Spaceship spaceship) throws RuntimeException, FlightMissionFillingException {
        try {
            spaceshipService.assignSpaceshipOnMission(spaceship);
        } catch (UnreachableSpaceItemException ex) {
            logger.error("Exception was thrown: {}", ex.toString());
            FlightMissionFillingException flightMissionFillingEx = new FlightMissionFillingException("Spaceship is already assigned", spaceship);
            logger.error("Exception was thrown: {}", flightMissionFillingEx.getMessage());
            throw flightMissionFillingEx;
        }

        if (flightMission.getAssignedSpaceship() != null || flightMission.getDistance() > spaceship.getDistance()) {
            spaceship.setIsReadyForNextMissions(true);
            FlightMissionFillingException exception = new FlightMissionFillingException("FlightMission is already has assigned spaceship or input spaceship has too small max distance for the mission", flightMission, spaceship);
            logger.error("Exception was thrown: {}", exception.getMessage());
            throw exception;
        } else {
            flightMission.setAssignedSpaceship(spaceship);
        }
        return spaceship;
    }

    @Override
    // todo maybe replace Runtime exception by checked exception
    public CrewMember assignCrewMemberOnMission(FlightMission flightMission, CrewMember crewMember) throws FlightMissionFillingException {
        crewService.assignCrewMemberOnMission(crewMember);

        Role newRole = crewMember.getRole();
        Spaceship spaceship = flightMission.getAssignedSpaceship();
        if (spaceship == null) {
            FlightMissionFillingException exception = new FlightMissionFillingException("There is no Spaceship assigned to FlightMission", flightMission);
            logger.error("Exception was thrown: {}", exception.getMessage());
            throw exception;
        }

        short countOfSameRoleMembers = (short) flightMission.getAssignedCrew().stream().filter(e -> e.getRole().equals(newRole)).count();

        if (spaceship.getCrew().get(newRole) == countOfSameRoleMembers) {
            throw new FlightMissionFillingException("Spaceship of mission is already filled, cannot add new CrewMember", flightMission, crewMember);
        } else {
            flightMission.getAssignedCrew().add(crewMember);
        }

        return crewMember;
    }

    @Override
    public void clearFile() {
        missionRepository.clear();
    }

    @Override
    public void crush(FlightMission flightMission) {
        flightMission.setMissionStatus(MissionStatus.FAILED);
        spaceshipService.deleteSpaceship(flightMission.getAssignedSpaceship());
        flightMission.getAssignedCrew().forEach(crewService::deleteCrewMember);
    }
}
