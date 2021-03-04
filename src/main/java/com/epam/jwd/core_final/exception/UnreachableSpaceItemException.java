package com.epam.jwd.core_final.exception;

import com.epam.jwd.core_final.domain.factory.impl.BaseEntity;

import java.util.Objects;

public class UnreachableSpaceItemException extends RuntimeException {
    private final BaseEntity entity;

    public UnreachableSpaceItemException(BaseEntity entity) {
        this.entity = entity;
    }

    public UnreachableSpaceItemException(String message) {
        super(message);
        this.entity = null;
    }

    @Override
    public String getMessage() {
        String msg = super.getMessage();
        return Objects.requireNonNullElseGet(msg + ".Entity: " + entity, () -> "UnreachableSpaceItemException was throw with object: " + entity);
    }


}
