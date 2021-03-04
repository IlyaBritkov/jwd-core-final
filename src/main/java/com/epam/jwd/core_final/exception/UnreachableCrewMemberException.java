package com.epam.jwd.core_final.exception;

import com.epam.jwd.core_final.domain.factory.impl.CrewMember;

import java.util.Objects;

public class UnreachableCrewMemberException extends RuntimeException {
    private final CrewMember crewMember;

    public UnreachableCrewMemberException(CrewMember crewMember) {
        this.crewMember = crewMember;
    }

    public UnreachableCrewMemberException(String message) {
        super(message);
        this.crewMember = null;
    }

    @Override
    public String getMessage() {
        String msg = super.getMessage();
        return Objects.requireNonNullElseGet(msg + ".Entity: " + crewMember, () -> "UnreachableCrewMemberException was throw with object: " + crewMember);
    }


}
