package com.epam.jwd.core_final.exception;

import java.util.Arrays;
import java.util.Objects;

public class FlightMissionFillingException extends Exception {
    private final Object[] args;

    public FlightMissionFillingException(String message) {
        super(message);
        this.args = null;
    }

    public FlightMissionFillingException(String message, Object... args) {
        super(message);
        this.args = args;
    }

    @Override
    public String getMessage() {
        String msg = super.getMessage();
        return Objects.requireNonNullElse(msg + ".Args: " + Arrays.toString(args),
                msg);
    }
}
