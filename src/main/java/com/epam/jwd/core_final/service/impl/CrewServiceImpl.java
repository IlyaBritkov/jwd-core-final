package com.epam.jwd.core_final.service.impl;

import com.epam.jwd.core_final.Main;
import com.epam.jwd.core_final.context.ApplicationContext;
import com.epam.jwd.core_final.criteria.CrewMemberCriteria;
import com.epam.jwd.core_final.criteria.Criteria;
import com.epam.jwd.core_final.domain.CashedEntity;
import com.epam.jwd.core_final.domain.factory.impl.CrewMember;
import com.epam.jwd.core_final.exception.UnreachableCrewMemberException;
import com.epam.jwd.core_final.repository.CrewRepository;
import com.epam.jwd.core_final.repository.impl.CrewRepositoryImpl;
import com.epam.jwd.core_final.service.CrewService;
import com.epam.jwd.core_final.util.ReflectUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Singleton
 **/
public class CrewServiceImpl implements CrewService {
    private final Logger logger = LoggerFactory.getLogger(CrewServiceImpl.class);
    private static CrewServiceImpl INSTANCE;
    private final CrewRepository crewRepository = CrewRepositoryImpl.getInstance();

    private final ApplicationContext context = Main.getApplicationContext();

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
        if (!context.isCashValid()) {
            try {
                context.refreshCash(CrewMember.class);
            } catch (IOException e) {
                logger.error("Exception was thrown: {}", e.toString());
                e.printStackTrace();
            }
        }
        return context.retrieveBaseEntityList(CrewMember.class).stream().filter(CashedEntity::isValid).map(CashedEntity::getEntity).collect(Collectors.toList());
    }

    @Override
    public List<CrewMember> findAllCrewMembersByCriteria(Criteria<? extends CrewMember> criteria) {
        List<Field> requiredFieldsList = ReflectUtil.getDeepNotNullFields(CrewMemberCriteria.class, criteria);
        List<Field> entityFieldsList = ReflectUtil.getDeepAllFields(CrewMember.class);

        List<CrewMember> crewMemberList = findAllCrewMembers();

        List<CrewMember> crewMembers = crewMemberList
                .stream()
                .filter(entity -> ReflectUtil.compareOnFields(requiredFieldsList, criteria,
                        entityFieldsList, entity)).collect(Collectors.toList());
        if (crewMembers.size() == 0) {
            try {
                context.refreshCash(CrewMember.class);
            } catch (IOException e) {
                logger.error("Exception was thrown: {}", e.toString());
                e.printStackTrace();
            }
        }

        crewMemberList = findAllCrewMembers();
        crewMembers = crewMemberList
                .stream()
                .filter(entity -> ReflectUtil.compareOnFields(requiredFieldsList, criteria,
                        entityFieldsList, entity)).collect(Collectors.toList());

        return crewMembers;
    }

    @Override
    public Optional<CrewMember> findCrewMemberByCriteria(Criteria<? extends CrewMember> criteria) {
        Optional<CrewMember> crewMember = findAllCrewMembersByCriteria(criteria)
                .stream()
                .findFirst();
        if (crewMember.isEmpty()) {
            try {
                context.refreshCash(CrewMember.class);
            } catch (IOException e) {
                logger.error("Exception was thrown: {}", e.toString());
                e.printStackTrace();
            }
        }
        return findAllCrewMembersByCriteria(criteria).stream().findFirst();
    }

    @Override
    public void assignCrewMemberOnMission(CrewMember crewMember) throws RuntimeException {
        if (!crewMember.getIsReadyForNextMissions()) {
            throw new UnreachableCrewMemberException(crewMember);
        } else {
            crewMember.setIsReadyForNextMissions(false);
        }
    }

    @Override
    public CrewMember createCrewMember(CrewMember crewMember) throws RuntimeException {
        crewRepository.createCrewMember(crewMember);
        context.setCashValid(false);
        return crewMember;
    }

    @Override
    public CrewMember deleteCrewMember(CrewMember crewMember) {
        context.deleteFromCash(crewMember);
        context.setCashValid(false);
        crewRepository.deleteCrewMember(crewMember);
        return crewMember;
    }
}
