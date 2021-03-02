package com.epam.jwd.core_final.exception;

import java.util.Arrays;

public class InvalidStateException extends Exception {
    private final Object[] args;

    public InvalidStateException() {
        args = null;
    }

    public InvalidStateException(Object[] args) {
        this.args = args;
    }

    @Override
    public String getMessage() {
        if (args != null) {
            return "Exception was thrown with args: " + Arrays.toString(args);
        } else {
            return super.getMessage();
        }
    }
}
