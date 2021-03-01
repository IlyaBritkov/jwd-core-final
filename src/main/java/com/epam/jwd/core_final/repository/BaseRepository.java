package com.epam.jwd.core_final.repository;

import com.epam.jwd.core_final.domain.factory.impl.BaseEntity;

import java.io.IOException;
import java.util.Collection;

public interface BaseRepository<T extends BaseEntity> {
    Collection<T> findAll() throws IOException;
}
