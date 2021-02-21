package com.epam.jwd.core_final.domain;

import com.epam.jwd.core_final.util.PropertyReaderUtil;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApplicationPropertiesTest {
    private final Logger logger = LoggerFactory.getLogger(ApplicationPropertiesTest.class);

    @Test
    public void instantiationOfApplicationPropertiesShouldExecutesWithoutExceptions() {
        PropertyReaderUtil.loadProperties();
        ApplicationProperties applicationProperties = new ApplicationProperties(PropertyReaderUtil.getProperties());
        logger.trace("ApplicationProperties' instance was created");
    }
}
