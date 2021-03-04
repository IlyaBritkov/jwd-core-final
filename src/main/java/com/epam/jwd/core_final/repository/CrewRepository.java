package com.epam.jwd.core_final.repository;

import com.epam.jwd.core_final.domain.factory.impl.CrewMember;

import java.io.IOException;
import java.util.Collection;

public interface CrewRepository {
    Collection<CrewMember> findAll() throws IOException;

    CrewMember createCrewMember(CrewMember crewMember) throws RuntimeException;

    CrewMember deleteCrewMember(CrewMember crewMember);

}
