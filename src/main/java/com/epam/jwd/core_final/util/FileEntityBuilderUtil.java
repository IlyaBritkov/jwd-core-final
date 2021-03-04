package com.epam.jwd.core_final.util;

import com.epam.jwd.core_final.domain.factory.impl.BaseEntity;
import com.epam.jwd.core_final.domain.factory.impl.CrewMember;
import com.epam.jwd.core_final.domain.factory.impl.Planet;
import com.epam.jwd.core_final.domain.factory.impl.Spaceship;
import com.epam.jwd.core_final.exception.UnknownEntityException;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

public class FileEntityBuilderUtil<T extends BaseEntity> {
    private final FileEntityBuilder<T> fileEntityBuilder;
    private final Pattern pattern;

    /**
     * @param tClass - entity stored in files
     **/
    @SuppressWarnings("unchecked")
    public FileEntityBuilderUtil(Class<T> tClass) {
        if (tClass == CrewMember.class) {
            pattern = Pattern.compile(".+?;"); // role,name,rank;
            fileEntityBuilder = (FileEntityBuilder<T>) new CrewMemberFileEntityBuilder();
        } else if (tClass == Spaceship.class) {
            pattern = Pattern.compile("(.+?[;|{\"}])+?$");// name;distance;crew {roleid:count,roleid:count,roleid:count,roleid:count}
            fileEntityBuilder = (FileEntityBuilder<T>) new SpaceshipFileEntityBuilder();
        } else if (tClass == Planet.class) {
            pattern = Pattern.compile("(.+?,)+?$"); // null,planet,null ...
            fileEntityBuilder = (FileEntityBuilder<T>) new PlanetFileEntityBuilder();
        } else {
            throw new UnknownEntityException(tClass.getSimpleName());
        }
    }

    /**
     * @param filePath - file we are reading from
     **/
    public Collection<T> getCollectionFromFile(String filePath) throws IOException {
        final Collection<T> collection = new HashSet<>();

        try (BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(Objects.requireNonNull(FileEntityBuilderUtil.class.getClassLoader().getResourceAsStream(filePath))))) {

            @Nullable List<String> hashInputFields = null;
            while (bufferedReader.ready()) {
                String line = bufferedReader.readLine().trim();
                if (line.startsWith("#")) {
                    hashInputFields = fileEntityBuilder.calculateHashInputFields(line);
                } else {
                    collection.addAll(fileEntityBuilder.createEntitiesFromString(pattern, hashInputFields, line));
                }
            }
        }
        return collection;
    }

    public T writeEntityToFile(String filePath, T entity) throws RuntimeException, IOException, URISyntaxException {
        fileEntityBuilder.writeEntityToFile(filePath, entity);
        return entity;
    }

     public T deleteEntityFromFile(String filePath,T entity) throws IOException, URISyntaxException {
         fileEntityBuilder.deleteEntityFromFile(filePath, entity);
         return entity;
     }
}
