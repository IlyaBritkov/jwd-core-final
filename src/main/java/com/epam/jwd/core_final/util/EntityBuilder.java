package com.epam.jwd.core_final.util;

import com.epam.jwd.core_final.domain.factory.impl.BaseEntity;

import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

public interface EntityBuilder<T extends BaseEntity> {
    List<String> calculateHashInputFields(String line);

    Collection<T> createEntities(Pattern pattern, List<String> hashInputFields, String line);
}
