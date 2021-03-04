package com.epam.jwd.core_final.util;

import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.domain.factory.impl.Spaceship;
import com.epam.jwd.core_final.domain.factory.impl.SpaceshipFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

class SpaceshipFileEntityBuilder implements FileEntityBuilder<Spaceship> {
    private final Logger logger = LoggerFactory.getLogger(SpaceshipFileEntityBuilder.class);
    private final SpaceshipFactory factory;

    SpaceshipFileEntityBuilder() {
        this.factory = SpaceshipFactory.getInstance();
    }

    @Override
    public List<String> calculateHashInputFields(String line) {
        return Arrays.stream(line.substring(1, line.length() - 1)
                .replace("{", ";")
                .replace(":", ";")
                .replace(",", ";")
                .replace("}", ";")
                .trim()
                .split(";")).map(String::trim).collect(Collectors.toList());
    }

    @Override
    public Collection<Spaceship> createEntitiesFromString(Pattern pattern, List<String> hashInputFields, String line) {
        Collection<Spaceship> collection = new HashSet<>();
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            String str = matcher.group()
                    .replace("{", ";")
                    .replace(":", ";")
                    .replace(",", ";")
                    .replace("}", ";")
                    .trim();
            str = str.substring(0, str.length() - 1).trim();

            logger.debug("Fields from spaceships file: {}", str);

            List<String> fields = Arrays.asList(str.split(";+"));

            Spaceship entity = buildEntity(hashInputFields, fields);
            collection.add(entity);
        }

        return collection;
    }

    @Override
    // todo
    public Spaceship writeEntityToFile(String filePath, Spaceship entity) throws IOException {
        return null;
    }

    @Override
    // todo
    public Spaceship deleteEntityFromFile(String filePath, Spaceship entity) throws IOException, URISyntaxException {
        return null;
    }

    private Spaceship buildEntity(List<String> hashInputFields, List<String> fields) {
        final String name = fields.get(0);
        final Long flightDistance = Long.valueOf(fields.get(1));

        final Map<Role, Short> crew = new HashMap<>();
        Role role1 = Role.resolveRoleById(Integer.parseInt(fields.get(2)));
        short count1 = Short.parseShort(fields.get(3));

        Role role2 = Role.resolveRoleById(Integer.parseInt(fields.get(4)));
        short count2 = Short.parseShort(fields.get(5));

        Role role3 = Role.resolveRoleById(Integer.parseInt(fields.get(6)));
        short count3 = Short.parseShort(fields.get(7));

        Role role4 = Role.resolveRoleById(Integer.parseInt(fields.get(8)));
        short count4 = Short.parseShort(fields.get(9));

        crew.put(role1, count1);
        crew.put(role2, count2);
        crew.put(role3, count3);
        crew.put(role4, count4);

        return factory.create(name, flightDistance, crew);
    }

}
