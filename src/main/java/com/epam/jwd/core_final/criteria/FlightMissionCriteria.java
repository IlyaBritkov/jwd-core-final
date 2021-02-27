package com.epam.jwd.core_final.criteria;

import com.epam.jwd.core_final.domain.MissionResult;
import com.epam.jwd.core_final.domain.factory.impl.CrewMember;
import com.epam.jwd.core_final.domain.factory.impl.FlightMission;
import com.epam.jwd.core_final.domain.factory.impl.Spaceship;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.util.List;

/**
 * Should be a builder for {@link FlightMission} fields
 */

@Getter
@Setter
@ToString(callSuper = true)
public class FlightMissionCriteria extends Criteria<FlightMission> {
    @Nullable
    private String missionName;
    @Nullable
    private LocalDate startDate;
    @Nullable
    private LocalDate endDate;
    @Nullable
    private Long distance;
    @Nullable
    private Spaceship assignedSpaceship;
    @Nullable
    private List<? extends CrewMember> assignedCrew;
    @Nullable
    private MissionResult missionResult;

    protected FlightMissionCriteria() {
    }

    public static FlightMissionCriteriaBuilder builder() {
        return new FlightMissionCriteriaBuilder(FlightMissionCriteria.class);
    }

    @Getter
    protected static class FlightMissionCriteriaBuilder extends CriteriaBuilder<FlightMissionCriteria, FlightMissionCriteriaBuilder> {
        @Nullable
        private String missionName;
        @Nullable
        private LocalDate startDate;
        @Nullable
        private LocalDate endDate;
        @Nullable
        private Long distance;
        @Nullable
        private Spaceship assignedSpaceship;
        @Nullable
        private List<? extends CrewMember> assignedCrew;
        @Nullable
        private MissionResult missionResult;

        public FlightMissionCriteriaBuilder(Class<FlightMissionCriteria> criteriaClass) {
            super(criteriaClass);
        }

        public FlightMissionCriteriaBuilder setMissionName(String missionName) {
            this.missionName = missionName;
            return this;
        }

        public FlightMissionCriteriaBuilder setStartDate(LocalDate startDate) {
            this.startDate = startDate;
            return this;
        }

        public FlightMissionCriteriaBuilder setEndDate(LocalDate endDate) {
            this.endDate = endDate;
            return this;
        }

        public FlightMissionCriteriaBuilder setDistance(Long distance) {
            this.distance = distance;
            return this;
        }

        public FlightMissionCriteriaBuilder setAssignedSpaceship(Spaceship assignedSpaceship) {
            this.assignedSpaceship = assignedSpaceship;
            return this;
        }

        public FlightMissionCriteriaBuilder setAssignedCrew(@Nullable List<? extends CrewMember> assignedCrew) {
            this.assignedCrew = assignedCrew;
            return this;
        }

        public FlightMissionCriteriaBuilder setMissionResult(@Nullable MissionResult missionResult) {
            this.missionResult = missionResult;
            return this;
        }

        @Override
        public FlightMissionCriteria build() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
            FlightMissionCriteria criteria = super.build();
            criteria.setMissionName(this.getMissionName());
            criteria.setStartDate(this.getStartDate());
            criteria.setEndDate(this.getEndDate());
            criteria.setDistance(this.getDistance());
            criteria.setAssignedSpaceship(this.getAssignedSpaceship());
            criteria.setAssignedCrew(this.getAssignedCrew());
            criteria.setMissionResult(this.getMissionResult());

            return criteria;
        }
    }


}
