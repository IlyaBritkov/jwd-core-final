package com.epam.jwd.core_final.domain.factory.impl;

import com.epam.jwd.core_final.domain.MissionStatus;
import com.epam.jwd.core_final.service.SpacemapService;
import com.epam.jwd.core_final.util.EntityIdGenerator;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
 * missionResult {@link MissionStatus}
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
    private List<CrewMember> assignedCrew;

    // custom getter
    @Setter
    private MissionStatus missionStatus;

    protected FlightMission(@NotNull String name, @NotNull String missionName, @NotNull LocalDateTime startDate,
                            @NotNull Planet from, @NotNull Planet to) {
        super(EntityIdGenerator.getNextId(FlightMission.class), name);
        this.missionName = missionName;
        this.startDate = startDate;
        this.from = from;
        this.to = to;
        this.assignedCrew = new ArrayList<>();
        calculateFlightData();
        final LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(startDate)) {
            missionStatus = MissionStatus.PLANNED;
        } else if (now.isAfter(startDate) && now.isBefore(endDate)) {
            missionStatus = MissionStatus.IN_PROGRESS;
        }
    }

    public MissionStatus getMissionStatus() {
        LocalDateTime now = LocalDateTime.now();

        if (now.isEqual(startDate) || now.isAfter(startDate) && now.isBefore(endDate)) {
            setMissionStatus(MissionStatus.IN_PROGRESS);
        } else if (now.isAfter(endDate)) {
            setMissionStatus(MissionStatus.COMPLETED);
        }
        return missionStatus;
    }

    private void calculateFlightData() {
        int calculatedTimeDistance = SpacemapService.getDistanceBetweenPlanets(from, to);
        this.endDate = this.startDate.plusSeconds(calculatedTimeDistance);
        this.distance = (long) calculatedTimeDistance;
    }
}
