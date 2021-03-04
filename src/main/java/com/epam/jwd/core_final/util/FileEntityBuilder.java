package com.epam.jwd.core_final.util;

import com.epam.jwd.core_final.domain.factory.impl.BaseEntity;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

public interface FileEntityBuilder<T extends BaseEntity> {
    List<String> calculateHashInputFields(String line);

    Collection<T> createEntitiesFromString(Pattern pattern, List<String> hashInputFields, String line);

    T writeEntityToFile(String filePath, T entity) throws IOException, URISyntaxException;

    T deleteEntityFromFile(String filePath, T entity) throws IOException, URISyntaxException;
}
