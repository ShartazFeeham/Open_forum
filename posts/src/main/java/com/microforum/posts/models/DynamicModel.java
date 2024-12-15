package com.microforum.posts.models;

import java.util.HashMap;
import java.util.Map;

public class DynamicModel {

    private Map<String, Object> map = new HashMap<>();

    public static DynamicModel builder() {
        return new DynamicModel();
    }

    public DynamicModel addParam(String key, Object value) {
        map.put(key, value);
        return this;
    }

    public Map<String, Object> build() {
        return map;
    }
}
