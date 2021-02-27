package com.epam.jwd.core_final.domain.factory.impl;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

/**
 * Expected fields:
 * <p>
 * id {@link Long} - entity id
 * name {@link String} - entity name
 */
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public abstract class AbstractBaseEntity implements BaseEntity {
    @EqualsAndHashCode.Include
    private Long id;
    private String name;

    protected AbstractBaseEntity() {
    }

    public AbstractBaseEntity(@NotNull Long id,@NotNull String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }
}
