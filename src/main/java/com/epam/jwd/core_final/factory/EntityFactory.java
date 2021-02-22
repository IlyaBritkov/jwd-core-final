package com.epam.jwd.core_final.factory;

import com.epam.jwd.core_final.domain.BaseEntity;

// todo: realise sense of factory when entities is public
public interface EntityFactory<T extends BaseEntity> {

    T create(Object... args);
}
