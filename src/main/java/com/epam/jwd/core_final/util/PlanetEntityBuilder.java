package com.epam.jwd.core_final.util;

import com.codepoetics.protonpack.StreamUtils;
import com.epam.jwd.core_final.domain.factory.impl.Planet;
import com.epam.jwd.core_final.domain.factory.impl.PlanetFactory;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class PlanetEntityBuilder implements EntityBuilder<Planet> {
    private final PlanetFactory factory;
    private int rowCounter = 1;

    PlanetEntityBuilder() {
        this.factory = PlanetFactory.getInstance();
    }

    @Override
    public List<String> calculateHashInputFields(String line) {
        return null;
    }

    /**
     * Method creates entities founded in input line
     **/
    @Override
    public Collection<Planet> createEntities(Pattern pattern, List<String> hashInputFields, String line) {
        Collection<Planet> collection = new HashSet<>();
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            String str = matcher.group().trim();
            str = str.substring(0, str.length() - 1).trim();

            List<String> fields = Arrays.asList(str.split(","));
            collection.addAll(buildEntities(fields));
        }

        return collection;
    }

    private Collection<Planet> buildEntities(List<String> fields) {
        Collection<Planet> collection = new HashSet<>();
        StreamUtils.zipWithIndex(fields.stream()).forEach(p -> {
            if (!p.getValue().equals("null")) {
                collection.add(buildEntity(null, Arrays.asList(p.getValue(), String.valueOf(p.getIndex()), String.valueOf(rowCounter++))));
            }
        });
        return collection;
    }

    private Planet buildEntity(@Nullable List<String> hashInputFields, List<String> fields) {
        final String name = fields.get(0);
        final int x = Integer.parseInt(fields.get(1));
        final int y = Integer.parseInt(fields.get(2));

        return factory.create(name, x, y);
    }
}
