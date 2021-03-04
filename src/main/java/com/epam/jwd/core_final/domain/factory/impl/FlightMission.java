package com.epam.jwd.core_final.domain.factory.impl;

import com.epam.jwd.core_final.domain.MissionResult;
import com.epam.jwd.core_final.service.SpacemapService;
import com.epam.jwd.core_final.util.EntityIdGenerator;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Expected fields:
 * <p>
 * missions name {@link String}
 * start date {@link java.time.LocalDateTime}
 * end date {@link java.time.LocalDateTime}
 * distance {@link Long} - missions distance
 * assignedSpaceShift {@link Spaceship} - not defined by default
 * assignedCrew {@link java.util.List<CrewMember>} - list of missions members based on ship capacity - not defined by default
 * missionResult {@link MissionResult}
 * from {@link Planet}
 * to {@link Planet}
 */

@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@ToString(callSuper = true)
public class FlightMission extends AbstractBaseEntity {
    @NotNull
    @Getter
    private String missionName;

    @NotNull
    @Getter
    @EqualsAndHashCode.Include
    private LocalDateTime startDate;

    @NotNull
    @Getter
    @EqualsAndHashCode.Include
    private LocalDateTime endDate;

    @NotNull
    @Getter
    private Long distance;

    @NotNull
    @Getter
    private Planet from;

    @NotNull
    @Getter
    private Planet to;

    @Getter
    private Spaceship assignedSpaceship;

    @Getter
    private List<? extends CrewMember> assignedCrew;

    private MissionResult missionResult;


    protected FlightMission(@NotNull String name, @NotNull String missionName, @NotNull LocalDateTime startDate,
                            @NotNull Planet from, @NotNull Planet to) {
        super(EntityIdGenerator.getNextId(FlightMission.class), name);
        this.missionName = missionName;
        this.startDate = startDate;
        this.from = from;
        this.to = to;
        calculateFlightData();
        final LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(startDate)) {
            missionResult = MissionResult.PLANNED;
        } else if (now.isAfter(startDate) && now.isBefore(endDate)) {
            missionResult = MissionResult.IN_PROGRESS;
        }
    }

    public MissionResult getMissionResult() {
        LocalDateTime now = LocalDateTime.now();

        if (now.isEqual(startDate) || now.isAfter(startDate) && now.isBefore(endDate)) {
            setMissionResult(MissionResult.IN_PROGRESS);
        } else if (now.isAfter(endDate)) {
            setMissionResult(MissionResult.COMPLETED);
        }
        return missionResult;
    }

    private void calculateFlightData() {
        this.endDate = this.startDate.plusSeconds(SpacemapService.getDistanceBetweenPlanets(from, to));
    }
}
