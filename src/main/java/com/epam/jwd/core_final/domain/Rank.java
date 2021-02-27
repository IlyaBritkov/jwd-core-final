package com.epam.jwd.core_final.domain;

import com.epam.jwd.core_final.exception.UnknownEntityException;

public enum Rank implements BaseEntity {
    TRAINEE(1L),
    SECOND_OFFICER(2L),
    FIRST_OFFICER(3L),
    CAPTAIN(4L);

    private final Long id;

    Rank(Long id) {
        this.id = id;
    }

    @Override
    public Long getId() {
        return id;
    }

    /**
     * to do via java.lang.enum methods!
     */
    @Override
    public String getName() {
        return this.name();
    }

    /**
     * to do via java.lang.enum methods!
     *
     * @throws UnknownEntityException if such id does not exist
     */
    public static Rank resolveRankById(int id) {
        try {
            return Rank.values()[id - 1];
        } catch (Exception e) {
            throw new UnknownEntityException(Rank.class.getSimpleName(), id);
        }
    }
}
