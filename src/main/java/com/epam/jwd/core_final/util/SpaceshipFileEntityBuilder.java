package com.epam.jwd.core_final.util;

import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.domain.factory.impl.Spaceship;
import com.epam.jwd.core_final.domain.factory.impl.SpaceshipFactory;
import com.epam.jwd.core_final.exception.EntityCreationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.lang.reflect.Field;
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

    @Override
    public Spaceship writeEntityToFile(String filePath, Spaceship entity) throws IOException, URISyntaxException {
        if (checkIfStringEntityIsAlreadyExist(filePath, entity.getName())) {
            throw new EntityCreationException("Entity already exists in file", filePath, entity);
        } else {
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(SpaceshipFileEntityBuilder.class.getClassLoader()
                    .getResourceAsStream(filePath))))) {
                String hashLine = bufferedReader.readLine();
                String stringEntity = createStringRepresentationFromEntity(calculateHashInputFields(hashLine), entity);
                writeEntityToFile(filePath, stringEntity);
            }
        }
        return entity;
    }

    private void writeEntityToFile(String filePath, String stringEntity) throws IOException, URISyntaxException {
        File file = new File(Objects.requireNonNull(SpaceshipFileEntityBuilder.class.getClassLoader().getResource(filePath)).toURI());
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true))) {
            bufferedWriter.write("\n" + stringEntity);
            bufferedWriter.close();
            logger.info("String representation of entity: {} was written to file {}", stringEntity, filePath);
        }
    }

    @Override
    public Spaceship deleteEntityFromFile(String filePath, Spaceship entity) throws IOException, URISyntaxException {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(SpaceshipFileEntityBuilder.class.getClassLoader()
                .getResourceAsStream(filePath))))) {
            String hashLine = bufferedReader.readLine();
            String stringEntity = createStringRepresentationFromEntity(calculateHashInputFields(hashLine), entity);
            deleteEntityFromFile(filePath, stringEntity);
        }
        return entity;
    }

    private void deleteEntityFromFile(String filePath, String stringEntity) throws IOException, URISyntaxException {
        File file = new File(Objects.requireNonNull(SpaceshipFileEntityBuilder.class.getClassLoader().getResource(filePath)).toURI());
        File temp = File.createTempFile("spaceships", "", file.getParentFile());

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
             BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(temp))) {
            bufferedWriter.write(bufferedReader.readLine()); // write hash to first line
            String line;
            while (bufferedReader.ready() && (line = bufferedReader.readLine().replace(stringEntity, "")).trim().length() > 0) {
                bufferedWriter.write("\n" + line);
            }
        }
        file.delete();
        temp.renameTo(file);
        logger.info("Entity: {} was deleted from file {}", stringEntity, filePath);
    }

    @SuppressWarnings("unchecked")
    private String createStringRepresentationFromEntity(List<String> hashInputFields, Spaceship entity) {
        List<Field> unsortedFields = ReflectUtil.getDeepAllFields(Spaceship.class).stream()
                .filter(e -> hashInputFields.contains(e.getName()))
                .collect(Collectors.toList());


        List<Field> sortedFields = new ArrayList<>();

        hashInputFields.forEach(hash -> unsortedFields.forEach(usortField -> {
            if (usortField.getName().equalsIgnoreCase(hash)) {
                sortedFields.add(usortField);
            }
        }));

        sortedFields.forEach(f -> f.setAccessible(true));

        // pattern - name;distance;crew {roleid:count,roleid:count,roleid:count,roleid:count}
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n");
        sortedFields.forEach(f -> {
            try {
                if (Map.class.isAssignableFrom(f.get(entity).getClass())) {
                    stringBuilder.append("{");
                    ((Map<Role, Short>) f.get(entity)).forEach((r, s) ->
                            stringBuilder.append(r.ordinal() + 1).append(":").append(s).append(","));
                    stringBuilder.deleteCharAt(stringBuilder.length() - 1).append("}");
                } else {
                    stringBuilder.append(f.get(entity)).append(";");
                }

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
        String result = stringBuilder.toString().trim();

        logger.debug("String: {} was composed from entity: {}", result, entity);

        return result;
    }

    // compare only by name
    private boolean checkIfStringEntityIsAlreadyExist(String filePath, String stringEntityData) throws IOException {
        try (BufferedReader bufferedReader =
                     new BufferedReader(new InputStreamReader(Objects.requireNonNull(SpaceshipFileEntityBuilder.class.getClassLoader()
                             .getResourceAsStream(filePath))))) {
            while (bufferedReader.ready()) {
                if (bufferedReader.readLine().contains(stringEntityData)) {
                    logger.debug("Entity: {} is already exist in file", stringEntityData);
                    return true;
                }
            }
            logger.debug("Entity: {} is not exist in file yet", stringEntityData);
        }
        return false;
    }

}
