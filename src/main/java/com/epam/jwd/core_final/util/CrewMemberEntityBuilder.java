package com.epam.jwd.core_final.util;

import com.epam.jwd.core_final.domain.Rank;
import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.domain.factory.impl.CrewMember;
import com.epam.jwd.core_final.domain.factory.impl.CrewMemberFactory;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class CrewMemberEntityBuilder implements EntityBuilder<CrewMember> {
    private final CrewMemberFactory factory;

    CrewMemberEntityBuilder() {
        factory = CrewMemberFactory.getInstance();
    }

    @Override
    public List<String> calculateHashInputFields(String line) {
        return Arrays.asList(line.substring(1, line.length() - 1).split(","));
    }

    @Override
    public Collection<CrewMember> createEntities(Pattern pattern, List<String> hashInputFields, String line) {
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

}
