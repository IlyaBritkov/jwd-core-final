package com.epam.jwd.core_final.exception;

import java.util.Arrays;

public class UnknownEntityException extends RuntimeException {

    private final String entityName;
    private final Object[] args;

    public UnknownEntityException(String entityName) {
        super();
        this.entityName = entityName;
        this.args = null;
    }

    public UnknownEntityException(String entityName, Object... args) {
        super();
        this.entityName = entityName;
        this.args = args;
    }

    @Override
    public String getMessage() {
        // you should use entityName, args (if necessary)
        if (args == null){
            return String.format("Cannot create %s\n", entityName);
        }else {
            return String.format("Cannot create %s with values: %s\n", entityName, Arrays.toString(args));
        }
    }
}
