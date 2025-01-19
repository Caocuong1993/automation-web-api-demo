package com.auto.api.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonUtils {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static String beautyObjectToJSon(Object object) {
        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        return gson.toJson(object);
    }

    public static String beautyStringJSon(String jsonString) {
        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        return gson.toJson(jsonString);
    }
}
