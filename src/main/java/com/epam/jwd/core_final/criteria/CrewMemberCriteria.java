package com.epam.jwd.core_final.criteria;

import com.epam.jwd.core_final.domain.Rank;
import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.domain.factory.impl.CrewMember;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.InvocationTargetException;

/**
 * Should be a builder for {@link CrewMember} fields
 */

@Getter
@Setter
@ToString(callSuper = true)
public class CrewMemberCriteria extends Criteria<CrewMember> {
    @Nullable
    private Role role;
    @Nullable
    private Rank rank;
    @Nullable
    private Boolean isReadyForNextMissions;

    protected CrewMemberCriteria() {
    }

    public static CrewMemberCriteriaBuilder builder() {
        return new CrewMemberCriteriaBuilder(CrewMemberCriteria.class);
    }

    @Getter
    protected static class CrewMemberCriteriaBuilder extends CriteriaBuilder<CrewMemberCriteria, CrewMemberCriteriaBuilder> {
        @Nullable
        private Role role;
        @Nullable
        private Rank rank;
        @Nullable
        private Boolean isReadyForNextMissions;

        protected CrewMemberCriteriaBuilder(Class<CrewMemberCriteria> criteriaClass) {
            super(criteriaClass);
        }

        public CrewMemberCriteriaBuilder setRole(Role role) {
            this.role = role;
            return this;
        }

        public CrewMemberCriteriaBuilder setRank(Rank rank) {
            this.rank = rank;
            return this;
        }

        public CrewMemberCriteriaBuilder setReadyForNextMissions(Boolean readyForNextMissions) {
            isReadyForNextMissions = readyForNextMissions;
            return this;
        }

        @Override
        public CrewMemberCriteria build() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
            CrewMemberCriteria criteria = super.build();
            criteria.setRole(this.getRole());
            criteria.setRank(this.getRank());
            criteria.setIsReadyForNextMissions(this.getIsReadyForNextMissions());
            return criteria;
        }
    }

}
