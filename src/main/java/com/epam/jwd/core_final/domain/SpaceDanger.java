package com.epam.jwd.core_final.domain;

import com.epam.jwd.core_final.criteria.FlightMissionCriteria;
import com.epam.jwd.core_final.domain.factory.impl.FlightMission;
import com.epam.jwd.core_final.service.MissionService;
import com.epam.jwd.core_final.service.impl.MissionServiceImpl;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Singleton
 **/
public class SpaceDanger {
    private static SpaceDanger INSTANCE;

    private final Logger logger = LoggerFactory.getLogger(SpaceDanger.class);

    @Getter
    @Setter
    private static volatile boolean isAlive = true;

    private final MissionService missionService = MissionServiceImpl.getInstance();

    @Getter
    private final int crushRate = 15_000;

    private SpaceDanger() {
    }

    public static SpaceDanger getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SpaceDanger();
        }
        return INSTANCE;
    }

    public void start() {
        logger.info("{} starts with crushRate = {}", this.getClass().getSimpleName(), crushRate);
        TimerTask crushTask = new TimerTask() {
            @Override
            public void run() {
                boolean successChance = new Random().nextInt(101) > 10;
                if (!successChance) {
                    Optional<FlightMission> flightMissionOptional = missionService.findAllMissionsByCriteria(
                            FlightMissionCriteria
                                    .builder()
                                    .setMissionStatus(MissionStatus.IN_PROGRESS)
                                    .build())
                            .stream().findAny();
                    if (flightMissionOptional.isPresent()) {
                        final FlightMission flightMission = flightMissionOptional.get();
                        missionService.crush(flightMission);
                        logger.debug("FlightMission + " + flightMission + " crashed");
                    }
                }
            }
        };

        Timer timer = new Timer("crush", true);
        timer.scheduleAtFixedRate(crushTask, 10_000, crushRate);
    }
}
