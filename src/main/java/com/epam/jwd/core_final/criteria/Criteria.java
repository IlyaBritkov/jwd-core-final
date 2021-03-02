package com.epam.jwd.core_final.criteria;

import com.epam.jwd.core_final.domain.factory.impl.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.InvocationTargetException;

/**
 * Should be a builder for {@link BaseEntity} fields
 */

@Getter
@Setter
@ToString
public abstract class Criteria<T1 extends BaseEntity> {
    @Nullable
    private Long id;
    @Nullable
    private String name;

    protected Criteria() {
    }

    /**
     * @type T2 - type of Builder
     * @type T3 - type of class that built by builder
     **/
    @Getter(AccessLevel.PROTECTED)
    protected abstract static class CriteriaBuilder<T3 extends Criteria<?>, T2 extends BaseBuilder<T2, T3>>
            implements BaseBuilder<T2, T3> {
        @Nullable
        private Long id;
        @Nullable
        private String name;

        private final Class<T3> criteriaClass;

        public CriteriaBuilder(Class<T3> criteriaClass) {
            this.criteriaClass = criteriaClass;
        }

        @SuppressWarnings("unchecked")
        public T2 setId(Long id) {
            this.id = id;
            return (T2) this;
        }

        @SuppressWarnings("unchecked")
        public T2 setName(String name) {
            this.name = name;
            return (T2) this;
        }

        public T3 build() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
            T3 criteria = criteriaClass.getDeclaredConstructor().newInstance();
            criteria.setId(this.id);
            criteria.setName(this.name);
            return criteria;
        }

    }
}
