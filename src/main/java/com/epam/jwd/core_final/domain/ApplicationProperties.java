package com.epam.jwd.core_final.domain;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Properties;

/**
 * This class should be IMMUTABLE!
 * <p>
 * Expected fields:
 * <p>
 * inputRootDir {@link String} - base dir for all input files
 * outputRootDir {@link String} - base dir for all output files
 * crewFileName {@link String}
 * missionsFileName {@link String}
 * spaceshipsFileName {@link String}
 * <p>
 * fileRefreshRate {@link Integer}
 * dateTimeFormat {@link String} - date/time format for {@link java.time.format.DateTimeFormatter} pattern
 */

@Getter
public final class ApplicationProperties {
    private final String inputRootDir;
    private final String outputRootDir;
    private final String crewFileName;
    private final String missionsFileName;
    private final String spaceshipsFileName;

    private final Integer fileRefreshRate;
    private final String dateTimeFormat;

    public ApplicationProperties(@NotNull Properties properties) {
        this.inputRootDir = Objects.requireNonNull(properties.getProperty("inputRootDir"));
        this.outputRootDir = Objects.requireNonNull(properties.getProperty("outputRootDir"));
        this.crewFileName = Objects.requireNonNull(properties.getProperty("crewFileName"));
        this.missionsFileName = Objects.requireNonNull(properties.getProperty("missionsFileName"));
        this.spaceshipsFileName = Objects.requireNonNull(properties.getProperty("spaceshipsFileName"));

        this.fileRefreshRate = Integer.valueOf(Objects.requireNonNull(properties.getProperty("fileRefreshRate")));
        this.dateTimeFormat = Objects.requireNonNull(properties.getProperty("dateTimeFormat"));

    }
}
