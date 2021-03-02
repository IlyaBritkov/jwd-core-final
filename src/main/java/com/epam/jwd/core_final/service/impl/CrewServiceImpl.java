package com.epam.jwd.core_final.service.impl;

import com.epam.jwd.core_final.Main;
import com.epam.jwd.core_final.context.ApplicationContext;
import com.epam.jwd.core_final.criteria.CrewMemberCriteria;
import com.epam.jwd.core_final.criteria.Criteria;
import com.epam.jwd.core_final.domain.factory.impl.CrewMember;
import com.epam.jwd.core_final.repository.CrewRepository;
import com.epam.jwd.core_final.repository.impl.CrewRepositoryImpl;
import com.epam.jwd.core_final.service.CrewService;
import com.epam.jwd.core_final.util.ReflectUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Singleton
 **/
public class CrewServiceImpl implements CrewService {
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
        return new ArrayList<>(context.retrieveBaseEntityList(CrewMember.class));
    }

    @Override
    public List<CrewMember> findAllCrewMembersByCriteria(Criteria<? extends CrewMember> criteria) {
        List<Field> requiredFieldsList = ReflectUtil.getDeepNotNullFields(CrewMemberCriteria.class, criteria);
        List<Field> entityFieldsList = ReflectUtil.getDeepAllFields(CrewMember.class);

        Set<CrewMember> crewMembersSet = (Set<CrewMember>) context.retrieveBaseEntityList(CrewMember.class);

        return crewMembersSet.stream().filter(entity -> ReflectUtil.compareOnFields(requiredFieldsList, criteria,
                entityFieldsList, entity)).collect(Collectors.toList());
    }

    @Override
    public Optional<CrewMember> findCrewMemberByCriteria(Criteria<? extends CrewMember> criteria) {
        return findAllCrewMembersByCriteria(criteria).stream().findFirst();
    }

    @Override
    public void assignCrewMemberOnMission(CrewMember crewMember) throws RuntimeException {
        // todo: ?
    }

    @Override
    public CrewMember createCrewMember(CrewMember crewMember) throws RuntimeException {
        return null;
    }

    @Override
    public CrewMember deleteCrewMember(CrewMember crewMember) {
        return null;
    }
}
