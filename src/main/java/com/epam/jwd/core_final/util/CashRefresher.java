package com.epam.jwd.core_final.util;

import com.epam.jwd.core_final.Main;
import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.domain.factory.impl.CrewMember;
import com.epam.jwd.core_final.domain.factory.impl.Planet;
import com.epam.jwd.core_final.domain.factory.impl.Spaceship;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Singleton
 **/
public class CashRefresher {
    private static CashRefresher INSTANCE;
    private final Logger logger = LoggerFactory.getLogger(CashRefresher.class);
    @Getter
    @Setter
    private static volatile boolean isAlive = true;

    private final NassaContext context = (NassaContext) Main.getApplicationContext();

    private final int fileRefreshRate = NassaContext.getApplicationProperties().getFileRefreshRate();

    private CashRefresher() {
    }

    public static CashRefresher getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CashRefresher();
        }
        return INSTANCE;
    }

    public void start() {
        logger.info("{} starts with fileFreshRate = {}", this.getClass().getSimpleName(), fileRefreshRate);
        TimerTask refreshTask = new TimerTask() {
            @Override
            public void run() {
                context.refreshCash(CrewMember.class);
                context.refreshCash(Spaceship.class);
                context.refreshCash(Planet.class);
                logger.debug("CrewMember, Spaceship and Planet cashes were refreshed");
            }
        };

        Timer timer = new Timer("refresh", true);
        timer.scheduleAtFixedRate(refreshTask, 0, fileRefreshRate);
    }
}
