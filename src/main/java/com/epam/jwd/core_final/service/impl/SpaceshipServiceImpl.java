package com.epam.jwd.core_final.service.impl;

import com.epam.jwd.core_final.Main;
import com.epam.jwd.core_final.context.ApplicationContext;
import com.epam.jwd.core_final.criteria.Criteria;
import com.epam.jwd.core_final.criteria.SpaceshipCriteria;
import com.epam.jwd.core_final.domain.CashedEntity;
import com.epam.jwd.core_final.domain.factory.impl.CrewMember;
import com.epam.jwd.core_final.domain.factory.impl.Spaceship;
import com.epam.jwd.core_final.exception.UnreachableSpaceItemException;
import com.epam.jwd.core_final.repository.SpaceshipsRepository;
import com.epam.jwd.core_final.repository.impl.SpaceshipsRepositoryImpl;
import com.epam.jwd.core_final.service.SpaceshipService;
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
// todo cover by tests
public class SpaceshipServiceImpl implements SpaceshipService {
    private final Logger logger = LoggerFactory.getLogger(SpaceshipServiceImpl.class);
    private static SpaceshipServiceImpl INSTANCE;
    private final SpaceshipsRepository spaceshipsRepository = SpaceshipsRepositoryImpl.getInstance();

    private final ApplicationContext context = Main.getApplicationContext();

    private SpaceshipServiceImpl() {
    }

    public static SpaceshipServiceImpl getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SpaceshipServiceImpl();
        }
        return INSTANCE;
    }

    @Override
    public List<Spaceship> findAllSpaceships() {
        if (!context.isCashValid(Spaceship.class)) {
            try {
                context.refreshCash(Spaceship.class);
            } catch (IOException e) {
                logger.error("Exception was thrown: {}", e.toString());
                e.printStackTrace();
            }
        }
        return context.retrieveBaseEntityList(Spaceship.class).stream()
                .filter(CashedEntity::isValid)
                .map(CashedEntity::getEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<Spaceship> findAllSpaceshipsByCriteria(Criteria<? extends Spaceship> criteria) {
        List<Field> requiredFieldsList = ReflectUtil.getDeepNotNullFields(SpaceshipCriteria.class, criteria);
        List<Field> entityFieldsList = ReflectUtil.getDeepAllFields(Spaceship.class);

        List<Spaceship> spaceshipList = findAllSpaceships();

        List<Spaceship> spaceships = findAllByFieldsOfCriteria(requiredFieldsList, criteria, entityFieldsList, spaceshipList);
        if (spaceships.size() == 0) {
            try {
                context.refreshCash(Spaceship.class);
            } catch (IOException e) {
                logger.error("Exception was thrown: {}", e.toString());
                e.printStackTrace();
            }
        }

        spaceshipList = findAllSpaceships();
        spaceships = findAllByFieldsOfCriteria(requiredFieldsList, criteria, entityFieldsList, spaceshipList);

        return spaceships;
    }

    private List<Spaceship> findAllByFieldsOfCriteria(List<Field> requiredFieldsList, Criteria<? extends Spaceship> criteria,
                                                      List<Field> entityFieldsList, List<Spaceship> spaceshipList) {
        return spaceshipList
                .stream()
                .filter(entity -> ReflectUtil.compareOnFields(requiredFieldsList, criteria,
                        entityFieldsList, entity))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Spaceship> findSpaceshipByCriteria(Criteria<? extends Spaceship> criteria) {
        Optional<Spaceship> spaceship = findAllSpaceshipsByCriteria(criteria)
                .stream()
                .findFirst();
        if (spaceship.isEmpty()) {
            try {
                context.refreshCash(CrewMember.class);
            } catch (IOException e) {
                logger.error("Exception was thrown: {}", e.toString());
                e.printStackTrace();
            }
        }
        return findAllSpaceshipsByCriteria(criteria).stream().findFirst();
    }

    @Override
    public void assignSpaceshipOnMission(Spaceship spaceship) throws RuntimeException {
        if (!spaceship.getIsReadyForNextMissions()) {
            throw new UnreachableSpaceItemException(spaceship);
        } else {
            spaceship.setIsReadyForNextMissions(false);
        }
    }

    @Override
    public Spaceship createSpaceship(Spaceship spaceship) throws RuntimeException {
        spaceshipsRepository.createSpaceship(spaceship);
        context.setCashValid(Spaceship.class, false);
        return spaceship;
    }

    @Override
    public Spaceship deleteSpaceship(Spaceship spaceship) {
        context.deleteFromCash(spaceship);
        spaceshipsRepository.deleteSpaceship(spaceship);
        return spaceship;
    }
}
