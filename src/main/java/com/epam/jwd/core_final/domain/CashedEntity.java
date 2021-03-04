package com.epam.jwd.core_final.domain;

import com.epam.jwd.core_final.domain.factory.impl.BaseEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/***
 * Wrapper for {@link BaseEntity} implementation
 * **/
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class CashedEntity<T extends BaseEntity> implements BaseEntity {
    @Setter
    private boolean isValid = true;
    @EqualsAndHashCode.Include
    private final T entity;

    public CashedEntity(T entity) {
        this.entity = entity;
    }

    @Override
    public Long getId() {
        return entity.getId();
    }

    @Override
    public String getName() {
        return entity.getName();
    }
}
