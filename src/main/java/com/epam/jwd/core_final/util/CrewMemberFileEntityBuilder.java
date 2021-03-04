package com.epam.jwd.core_final.util;

import com.epam.jwd.core_final.domain.Rank;
import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.domain.factory.impl.CrewMember;
import com.epam.jwd.core_final.domain.factory.impl.CrewMemberFactory;
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

class CrewMemberFileEntityBuilder implements FileEntityBuilder<CrewMember> {
    private final Logger logger = LoggerFactory.getLogger(CrewMemberFileEntityBuilder.class);
    private final CrewMemberFactory factory;

    CrewMemberFileEntityBuilder() {
        factory = CrewMemberFactory.getInstance();
    }

    @Override
    public List<String> calculateHashInputFields(String line) {
        return Arrays.asList(line.substring(1, line.length() - 1).split(","));
    }

    @Override
    public Collection<CrewMember> createEntitiesFromString(Pattern pattern, List<String> hashInputFields, String line) {
        Collection<CrewMember> collection = new HashSet<>();
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            String str = matcher.group().trim();
            List<String> fields = Arrays.asList(str.substring(0, str.length() - 1).split(","));
            CrewMember entity = buildEntity(hashInputFields, fields);
            collection.add(entity);
        }

        return collection;
    }

    public CrewMember buildEntity(List<String> hashInputFields, List<String> fields) {
        final Role role = Role.resolveRoleById(Integer.parseInt(fields.get(0)));
        final String name = fields.get(1);
        final Rank rank = Rank.resolveRankById(Integer.parseInt(fields.get(2)));
        return factory.create(role, name, rank);
    }


    @Override
    public CrewMember writeEntityToFile(String filePath, CrewMember entity) throws RuntimeException, IOException, URISyntaxException {
        if (checkIfStringEntityIsAlreadyExist(filePath, entity.getName())) {
            throw new EntityCreationException("Entity already exists in file", filePath, entity);
        } else {
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(CrewMemberFileEntityBuilder.class.getClassLoader()
                    .getResourceAsStream(filePath))))) {
                String hashLine = bufferedReader.readLine();
                String stringEntity = createStringFromEntity(calculateHashInputFields(hashLine), entity);
                writeEntityToFile(filePath, stringEntity);
            }
        }
        return entity;
    }

    private void writeEntityToFile(String filePath, String stringEntity) throws IOException, URISyntaxException {
        File file = new File(Objects.requireNonNull(CrewMemberFileEntityBuilder.class.getClassLoader().getResource(filePath)).toURI());
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true))) {
            bufferedWriter.write(stringEntity);
            bufferedWriter.close();
            logger.info("String representation of entity: {} was written to file {}", stringEntity, filePath);
        }
    }

    @Override
    public CrewMember deleteEntityFromFile(String filePath, CrewMember entity) throws IOException, URISyntaxException {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(CrewMemberFileEntityBuilder.class.getClassLoader()
                .getResourceAsStream(filePath))))) {
            String hashLine = bufferedReader.readLine();
            String stringEntity = createStringFromEntity(calculateHashInputFields(hashLine), entity);
            deleteEntityFromFile(filePath, stringEntity);
        }
        return entity;
    }

    private void deleteEntityFromFile(String filePath, String stringEntity) throws IOException, URISyntaxException {
        File file = new File(Objects.requireNonNull(CrewMemberFileEntityBuilder.class.getClassLoader().getResource(filePath)).toURI());
        File temp = File.createTempFile("crew", "", file.getParentFile());

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
             BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(temp))) {
            bufferedWriter.write(bufferedReader.readLine()+"\n"); // write hash to first line
            while (bufferedReader.ready()) {
                String line = bufferedReader.readLine().replace(stringEntity, "");
                bufferedWriter.write(line);
            }
        }
        file.delete();
        temp.renameTo(file);
        logger.info("Entity: {} was deleted from file {}", stringEntity, filePath);
    }

    private String createStringFromEntity(List<String> hashInputFields, CrewMember entity) {
        List<Field> unsortedFields = ReflectUtil.getDeepAllFields(CrewMember.class).stream()
                .filter(e -> hashInputFields.contains(e.getName()))
                .collect(Collectors.toList());


        List<Field> sortedFields = new ArrayList<>();

        hashInputFields.forEach(hash -> unsortedFields.forEach(usortField -> {
            if (usortField.getName().equalsIgnoreCase(hash)) {
                sortedFields.add(usortField);
            }
        }));

        sortedFields.forEach(f -> f.setAccessible(true));

        //pattern: role,name,rank;
        StringBuilder stringBuilder = new StringBuilder();
        sortedFields.forEach(f -> {
            try {
                if (f.get(entity).getClass().isEnum()) {
                    stringBuilder.append(((Enum<?>) f.get(entity)).ordinal() + 1).append(",");
                } else {
                    stringBuilder.append(f.get(entity)).append(",");
                }

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
        stringBuilder.deleteCharAt(stringBuilder.length() - 1).append(";");

        String result = stringBuilder.toString();

        logger.debug("String: {} was composed from entity: {}", result, entity);

        return result;
    }

    // compare only by name
    private boolean checkIfStringEntityIsAlreadyExist(String filePath, String stringEntityData) throws IOException {
        try (BufferedReader bufferedReader =
                     new BufferedReader(new InputStreamReader(Objects.requireNonNull(CrewMemberFileEntityBuilder.class.getClassLoader()
                             .getResourceAsStream(filePath))));) {
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
