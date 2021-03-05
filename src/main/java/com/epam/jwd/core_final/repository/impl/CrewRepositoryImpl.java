package com.epam.jwd.core_final.repository.impl;

import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.domain.factory.impl.CrewMember;
import com.epam.jwd.core_final.repository.CrewRepository;
import com.epam.jwd.core_final.util.FileEntityBuilderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Set;

/**
 * Singleton
 **/
public class CrewRepositoryImpl implements CrewRepository {
    private final Logger logger = LoggerFactory.getLogger(CrewRepositoryImpl.class);
    private static CrewRepositoryImpl INSTANCE;
    private final FileEntityBuilderUtil<CrewMember> fileEntityBuilder = new FileEntityBuilderUtil<>(CrewMember.class);
    private final String filePath = NassaContext.getApplicationProperties().getInputRootDir() + File.separator + NassaContext.getApplicationProperties().getCrewFileName();

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
        Collection<CrewMember> collection = fileEntityBuilder.getCollectionFromFile(filePath);
        logger.debug("All entities from input file were retrieved");
        return (Set<CrewMember>) collection;
    }

    @Override
    public CrewMember createCrewMember(CrewMember crewMember) throws RuntimeException {
        try {
            return fileEntityBuilder.writeEntityToFile(filePath, crewMember);
        } catch (IOException | URISyntaxException e) {
            logger.error("An exception was thrown: {}", e.toString());
            e.printStackTrace();
        }
        return crewMember;
    }

    @Override
    public CrewMember deleteCrewMember(CrewMember crewMember) {
        try {
            return fileEntityBuilder.deleteEntityFromFile(filePath, crewMember);
        } catch (IOException | URISyntaxException e) {
            logger.error("An exception was thrown: {}", e.toString());
            e.printStackTrace();
        }
        return crewMember;
    }

}
