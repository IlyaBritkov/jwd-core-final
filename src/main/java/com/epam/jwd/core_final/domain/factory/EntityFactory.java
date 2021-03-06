package com.epam.jwd.core_final.domain.factory;

import com.epam.jwd.core_final.domain.factory.impl.BaseEntity;
import com.epam.jwd.core_final.exception.FlightMissionFillingException;

public interface EntityFactory<T extends BaseEntity> {

    T create(Object... args) throws FlightMissionFillingException;
}
