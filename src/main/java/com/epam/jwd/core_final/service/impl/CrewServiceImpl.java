package com.epam.jwd.core_final.service.impl;

import com.epam.jwd.core_final.criteria.Criteria;
import com.epam.jwd.core_final.domain.factory.impl.CrewMember;
import com.epam.jwd.core_final.repository.impl.CrewRepositoryImpl;
import com.epam.jwd.core_final.service.CrewService;

import java.util.List;
import java.util.Optional;

/**
 * Singleton
 **/
public class CrewServiceImpl implements CrewService {
    private static CrewServiceImpl INSTANCE;
    private final CrewRepositoryImpl crewRepository = CrewRepositoryImpl.getInstance();

    private CrewServiceImpl() {
    }

    public static CrewServiceImpl getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CrewServiceImpl();
        }
        return INSTANCE;
    }

    @Override
    public List<CrewMember> findAllCrewMembers() {
        return null;
    }

    @Override
    public List<CrewMember> findAllCrewMembersByCriteria(Criteria<? extends CrewMember> criteria) {
        return null;
    }

    @Override
    public Optional<CrewMember> findCrewMemberByCriteria(Criteria<? extends CrewMember> criteria) {
        return Optional.empty();
    }

    @Override
    public CrewMember updateCrewMemberDetails(CrewMember crewMember) {
        return null;
    }

    @Override
    public void assignCrewMemberOnMission(CrewMember crewMember) throws RuntimeException {

    }

    @Override
    public CrewMember createCrewMember(CrewMember spaceship) throws RuntimeException {
        return null;
    }
}
