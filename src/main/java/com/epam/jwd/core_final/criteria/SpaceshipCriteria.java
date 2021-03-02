package com.epam.jwd.core_final.criteria;

import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.domain.factory.impl.Spaceship;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * Should be a builder for {@link Spaceship} fields
 */

@Getter
@Setter
@ToString(callSuper = true)
public class SpaceshipCriteria extends Criteria<Spaceship> {
    @Nullable
    private Map<Role, Short> crew;
    @Nullable
    private Long flightDistance;
    @Nullable
    private Boolean isReadyForNextMissions;

    protected SpaceshipCriteria() {
    }

    public static SpaceshipCriteriaBuilder builder() {
        return new SpaceshipCriteriaBuilder(SpaceshipCriteria.class);
    }

    @Getter(AccessLevel.PROTECTED)
    public static class SpaceshipCriteriaBuilder extends CriteriaBuilder<SpaceshipCriteria, SpaceshipCriteriaBuilder> {
        @Nullable
        private Map<Role, Short> crew;
        @Nullable
        private Long flightDistance;
        @Nullable
        private Boolean isReadyForNextMissions;

        protected SpaceshipCriteriaBuilder(Class<SpaceshipCriteria> criteriaClass) {
            super(criteriaClass);
        }

        public SpaceshipCriteriaBuilder setCrew(Map<Role, Short> crew) {
            this.crew = crew;
            return this;
        }

        public SpaceshipCriteriaBuilder setFlightDistance(Long flightDistance) {
            this.flightDistance = flightDistance;
            return this;
        }

        public SpaceshipCriteriaBuilder setReadyForNextMissions(Boolean readyForNextMissions) {
            isReadyForNextMissions = readyForNextMissions;
            return this;
        }

        @Override
        public SpaceshipCriteria build() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
            SpaceshipCriteria criteria = super.build();
            criteria.setCrew(this.getCrew());
            criteria.setFlightDistance(this.getFlightDistance());
            criteria.setIsReadyForNextMissions(this.getIsReadyForNextMissions());
            return criteria;
        }
    }
}
