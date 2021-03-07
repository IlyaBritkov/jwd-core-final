package com.epam.jwd.core_final.domain;

import com.epam.jwd.core_final.exception.UnknownEntityException;

public enum MissionStatus {
    CANCELLED,
    FAILED,
    PLANNED,
    IN_PROGRESS,
    COMPLETED;

    /**
     * @throws UnknownEntityException if such id does not exist
     */
    public static MissionStatus resolveMissionStatusById(int id) {
        try {
            return MissionStatus.values()[id - 1];
        } catch (Exception e) {
            throw new UnknownEntityException(MissionStatus.class.getSimpleName(), id);
        }
    }
}
