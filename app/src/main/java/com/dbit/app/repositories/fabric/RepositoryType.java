package com.dbit.app.repositories.fabric;

import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public enum RepositoryType {
    MEMORY("memory");

    private static Map<String, RepositoryType> value2Enum = initValue2Enum();
    private static Map<RepositoryType, String> enum2Value = initEnum2Value();

    private final String type;

    RepositoryType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public static RepositoryType getTypeByString(String type) {
        return value2Enum.get(type);
    }

    public static String getStringByRepositoryType(RepositoryType type) {
        return enum2Value.get(type);
    }

    private static Map<String, RepositoryType> initValue2Enum() {
        RepositoryType[] values = RepositoryType.values();
        Map<String, RepositoryType> map = new HashMap<>(values.length);

        for (RepositoryType enumElement : values) {
            map.put(enumElement.type, enumElement);
        }
        return Collections.unmodifiableMap(map);
    }

    private static Map<RepositoryType, String> initEnum2Value() {
        Map<RepositoryType, String> map = new EnumMap<>(RepositoryType.class);

        for (RepositoryType enumElement : RepositoryType.values()) {
            map.put(enumElement, enumElement.type);
        }

        return Collections.unmodifiableMap(map);
    }
}
