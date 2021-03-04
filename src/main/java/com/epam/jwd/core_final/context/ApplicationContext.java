package com.epam.jwd.core_final.context;

import com.epam.jwd.core_final.domain.CashedEntity;
import com.epam.jwd.core_final.domain.factory.impl.BaseEntity;
import com.epam.jwd.core_final.exception.InvalidStateException;

import java.io.IOException;
import java.util.Collection;

public interface ApplicationContext {

    <T extends BaseEntity> Collection<CashedEntity<T>> retrieveBaseEntityList(Class<T> tClass);

    void init() throws InvalidStateException;

    void emptyAllCash();

    <T extends BaseEntity> void emptyCash(Class<T> tClass);

    <T extends BaseEntity> void deleteFromCash(T entity);

    void refreshAllCash() throws InvalidStateException, IOException;

    <T extends BaseEntity> void refreshCash(Class<T> tClass) throws IOException;

    <T extends BaseEntity> boolean isCashValid(Class<T> tClass);

    <T extends BaseEntity> void setCashValid(Class<T> tClass, boolean isCashValid);
}
