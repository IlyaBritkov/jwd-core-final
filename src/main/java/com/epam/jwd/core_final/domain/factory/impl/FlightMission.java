package com.epam.jwd.core_final.domain.factory.impl;

import com.epam.jwd.core_final.domain.MissionResult;
import com.epam.jwd.core_final.util.EntityIdGenerator;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.List;

/**
 * Expected fields:
 * <p>
 * missions name {@link String}
 * start date {@link java.time.LocalDate}
 * end date {@link java.time.LocalDate}
 * distance {@link Long} - missions distance
 * assignedSpaceShift {@link Spaceship} - not defined by default
 * assignedCrew {@link java.util.List<CrewMember>} - list of missions members based on ship capacity - not defined by default
 * missionResult {@link MissionResult}
 */

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@ToString(callSuper = true)
public class FlightMission extends AbstractBaseEntity {
    @NotNull
    private String missionName;
    @NotNull
    private LocalDate startDate;
    @NotNull
    private LocalDate endDate;
    @NotNull
    private Long distance;

    private Spaceship assignedSpaceship;
    private List<? extends CrewMember> assignedCrew;
    private MissionResult missionResult;


    protected FlightMission(@NotNull String name, @NotNull String missionName, @NotNull LocalDate startDate, @NotNull Long distance) {
        super(EntityIdGenerator.getNextId(FlightMission.class), name);
        this.missionName = missionName;
        this.startDate = startDate;
        this.distance = distance;
    }
}
