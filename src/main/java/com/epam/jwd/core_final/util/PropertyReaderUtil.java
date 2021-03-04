package com.epam.jwd.core_final.util;

import com.epam.jwd.core_final.domain.ApplicationProperties;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class PropertyReaderUtil {
    private static final Logger logger = LoggerFactory.getLogger(PropertyReaderUtil.class);
    @Getter
    private static final Properties properties = new Properties();

    private PropertyReaderUtil() {
    }

    static {
        loadProperties();
    }

    /**
     * try-with-resource using FileInputStream
     *
     * @see {https://www.netjstech.com/2017/09/how-to-read-properties-file-in-java.html for an example}
     * <p>
     * as a result - you should populate {@link ApplicationProperties} with corresponding
     * values from property file
     */
    public static void loadProperties() {
        final String propertiesFileName = "application.properties";
        try (InputStream inputStream = PropertyReaderUtil.class.getClassLoader().getResourceAsStream(propertiesFileName)) {
            properties.load(inputStream);
            logger.info("Properties were loaded from file: {}", propertiesFileName);
        } catch (IOException e) {
            logger.error("Loading properties from file {} failed", propertiesFileName);
            e.printStackTrace();
        }
    }
}
