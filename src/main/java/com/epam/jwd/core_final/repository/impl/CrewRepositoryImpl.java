package com.epam.jwd.core_final.repository.impl;

import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.domain.factory.impl.CrewMember;
import com.epam.jwd.core_final.repository.CrewRepository;
import com.epam.jwd.core_final.util.FileEntityBuilderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collection;
import java.util.Set;

/**
 * Singleton
 **/
public class CrewRepositoryImpl implements CrewRepository {
    private final Logger logger = LoggerFactory.getLogger(CrewRepositoryImpl.class);
    private static CrewRepositoryImpl INSTANCE;
    private final FileEntityBuilderUtil<CrewMember> entityBuilder = new FileEntityBuilderUtil<>(CrewMember.class);

    private CrewRepositoryImpl() {
    }

    public static CrewRepositoryImpl getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CrewRepositoryImpl();
        }
        return INSTANCE;
    }

    @Override
    public Set<CrewMember> findAll() throws IOException {
        String filePath = NassaContext.getApplicationProperties().getInputRootDir() + "/" + NassaContext.getApplicationProperties().getCrewFileName();
        Collection<CrewMember> collection = entityBuilder.getCollectionFromFile(filePath);
        logger.debug("All entities from input file were retrieved");
        return (Set<CrewMember>) collection;
    }

}
