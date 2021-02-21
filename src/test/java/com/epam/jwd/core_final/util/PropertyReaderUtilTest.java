package com.epam.jwd.core_final.util;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class PropertyReaderUtilTest {
    private final static Logger logger = LoggerFactory.getLogger(PropertyReaderUtilTest.class);

    @Test
    public void loadPropertiesMethodShouldReadProperties() {
        PropertyReaderUtil.loadProperties();
        Properties properties = PropertyReaderUtil.getProperties();
        List<String> propertyKeysList = Arrays.asList(
                "inputRootDir",
                "outputRootDir",
                "crewFileName",
                "missionsFileName",
                "spaceshipsFileName",
                "fileRefreshRate",
                "dateTimeFormat"
        );

        propertyKeysList.forEach(key -> {
                    Assert.assertNotNull(String.format("Property %s is missed", key), properties.getProperty(key));
                    logger.debug(key + " = " + properties.getProperty(key));
                }
        );
    }
}
