package com.epam.jwd.core_final.exception;

import java.util.Arrays;
import java.util.Objects;

public class EntityCreationException extends RuntimeException {
    private final Object[] args;

    public EntityCreationException(Object... args) {
        this.args = args;
    }

    public EntityCreationException(String message, Object... args) {
        super(message);
        this.args = args;
    }

    @Override
    public String getMessage() {
        String msg = super.getMessage();
        return Objects.requireNonNullElseGet(msg + ".Args: " + Arrays.toString(args),
                () -> "Cannot create entity. Args: " + Arrays.toString(args));
    }
}
