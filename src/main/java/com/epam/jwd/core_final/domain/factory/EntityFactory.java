package com.epam.jwd.core_final.domain.factory;

import com.epam.jwd.core_final.domain.factory.impl.BaseEntity;

public interface EntityFactory<T extends BaseEntity> {

    T create(Object... args);
}
