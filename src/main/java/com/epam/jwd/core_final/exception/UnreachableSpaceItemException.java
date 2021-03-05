package com.epam.jwd.core_final.exception;

import com.epam.jwd.core_final.domain.factory.impl.BaseEntity;

public class UnreachableSpaceItemException extends RuntimeException {
    private final BaseEntity entity;

    public UnreachableSpaceItemException(BaseEntity entity) {
        this.entity = entity;
    }

    public UnreachableSpaceItemException(String message) {
        super(message);
        this.entity = null;
    }

    public UnreachableSpaceItemException(String message, BaseEntity entity) {
        super(message);
        this.entity = entity;
    }


    @Override
    public String getMessage() {
        String msg = super.getMessage();
        if (msg == null) {
            return "UnreachableSpaceItemException was throw with object" + entity;
        } else if (entity == null) {
            return msg;
        } else {
            return msg + " Args:" + entity;
        }
    }
}
