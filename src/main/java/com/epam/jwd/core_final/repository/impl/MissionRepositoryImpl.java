package com.epam.jwd.core_final.repository.impl;

import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.domain.ApplicationProperties;
import com.epam.jwd.core_final.domain.factory.impl.FlightMission;
import com.epam.jwd.core_final.repository.MissionRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

/**
 * Singleton
 **/
public class MissionRepositoryImpl implements MissionRepository {
    private final Logger logger = LoggerFactory.getLogger(MissionRepositoryImpl.class);
    private static MissionRepositoryImpl INSTANCE;
    private final ApplicationProperties properties;
    private final String filePath;

    private final ObjectMapper objectMapper;

    {
        properties = NassaContext.getApplicationProperties();
        filePath = properties.getOutputRootDir() + File.separator + NassaContext.getApplicationProperties().getMissionsFileName();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(properties.getDateTimeFormat());

        LocalDateTimeDeserializer dateTimeDeserializer = new LocalDateTimeDeserializer(formatter);
        LocalDateTimeSerializer dateTimeSerializer = new LocalDateTimeSerializer(formatter);

        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addDeserializer(LocalDateTime.class, dateTimeDeserializer);
        javaTimeModule.addSerializer(LocalDateTime.class, dateTimeSerializer);

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(javaTimeModule);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    private MissionRepositoryImpl() {
    }

    public static MissionRepositoryImpl getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MissionRepositoryImpl();
        }
        return INSTANCE;
    }

    @Override
    public void writeAllMissions(List<FlightMission> flightMissions) {
        flightMissions.forEach(this::writeMission);
        logger.debug("FlightMissions were written to file: {}", flightMissions);
    }

    @Override
    public FlightMission writeMission(FlightMission flightMission) {
        if (flightMission != null) {
            try {
                File missionsFile = new File(Objects.requireNonNull(MissionRepositoryImpl.class.getClassLoader().getResource(filePath)).toURI());
                objectMapper.writeValue(new FileWriter(missionsFile, true), flightMission);
            } catch (IOException | URISyntaxException ex) {
                logger.error("Exception was thrown: " + ex.toString());
                ex.printStackTrace();
            }
            logger.debug("FlightMission was written to file: {}", flightMission);
        }
        return flightMission;
    }

    @Override
    public void clear() {
        try {
            File file = new File(Objects.requireNonNull(MissionRepositoryImpl.class.getClassLoader().getResource(filePath)).toURI());
            FileWriter fileWriter = new FileWriter(file, false);
            PrintWriter printWriter = new PrintWriter(fileWriter, false);
            printWriter.flush();
            printWriter.close();
            fileWriter.close();
        } catch (URISyntaxException | IOException e) {
            logger.error("Exception was thrown {}", e.toString());
            e.printStackTrace();
        }
    }
}
