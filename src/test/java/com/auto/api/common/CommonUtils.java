package com.auto.api.common;

import com.auto.utils.LoggerUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;
import com.mysql.cj.util.StringUtils;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.assertj.core.util.Strings;
import org.junit.Assert;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;


public class CommonUtils {

    public static String convertObjectToJson(Object object) {
        return new Gson().toJson(object);
    }

    public static Object convertJsonToObject(String contentJson, Class<?> object) {
        return new Gson().fromJson(contentJson, object);
    }

    public static Object convertHashMapToObject(HashMap map, Class<?> cls) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        String jsonString = gson.toJson(map);
        return gson.fromJson(jsonString, cls);
    }

    public static void verifyResponseContains(String requiredActor, String subject, String actual, String expected) {
        String verifyMessage = "Verify " + subject + " ACTUAL was " + actual + " AND EXPECTED was " + expected;
        Assert.assertEquals(requiredActor, actual, expected);
    }


    public static String modifyJson(String jsonBody, String[] jsonPaths, String... values) {
        Configuration configuration = Configuration.builder().options(Option.SUPPRESS_EXCEPTIONS).build();
        DocumentContext parsed = JsonPath.using(configuration).parse(jsonBody);
        ObjectMapper mapper = new ObjectMapper();
        for (int i = 0; i < jsonPaths.length; i++) {
            if (!StringUtils.isNullOrEmpty(jsonPaths[i])) {
                switch (values[i]) {
                    case "{DELETE}":
                        parsed.delete(jsonPaths[i]);
                        break;
                    case "null":
                        parsed.set(jsonPaths[i], null);
                        break;
                    case "\"\"":
                        parsed.set(jsonPaths[i], "");
                        break;
                    case "true":
                    case "false":
                        parsed.set(jsonPaths[i], Boolean.parseBoolean(values[i]));
                        break;
                    default:
                        // Check if the value is an array
                        if (values[i].startsWith("[") && values[i].endsWith("]")) {
                            // Convert the string to a list and set it in the JSON
                            List arrayValue = null;
                            try {
                                arrayValue = mapper.readValue(values[i], List.class);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            parsed.set(jsonPaths[i], arrayValue);
                        } else {
                            parsed.set(jsonPaths[i], values[i]);
                        }
                }
            }
        }
        return parsed.jsonString();
    }

    public static String modifyJsonWithIntValue(String jsonBody, String[] jsonPaths, int... values) {
        Configuration configuration = Configuration.builder().options(Option.SUPPRESS_EXCEPTIONS).build();
        DocumentContext parsed = JsonPath.using(configuration).parse(jsonBody);
        for (int i = jsonPaths.length - 1; i >= 0; i--) {
            if (!StringUtils.isNullOrEmpty(jsonPaths[i])) {
                if (values[i] == -1) parsed.delete(jsonPaths[i]);
                else parsed.set(jsonPaths[i], values[i]);
            }
        }
        return parsed.jsonString();
    }


    public static String readDataFromFilePath(String nameJson) {
        String exampleRequest = "";
        File file = new File(PathUtils.PATH_FILE_JSON_FOLDER_SYSTEMTEST + nameJson);
        try {
            exampleRequest = org.apache.commons.io.FileUtils.readFileToString(file, StandardCharsets.UTF_8);

        } catch (IOException ignored) {
        }
        return exampleRequest;
    }

    public static HashMapExtend<String, Object> convertStringToMap(String args) {
        ObjectMapper mapper = new ObjectMapper();
        HashMapExtend<String, Object> map;
        try {
            map = mapper.readValue(args, new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return map;

    }

    public static void verifyResponseStatus(Response rest, int expected) {
        int actual = rest.getStatusCode();
        LoggerUtils.verifyResponse("Verify response status: actual is " + actual + ", expected is " + expected);
        Assertions.assertThat(actual).isEqualTo(expected);
    }

    public static void verifyResponseValueNullOrEmpty(String message, String actual) {
        LoggerUtils.verifyResponse(message + " actual is " + actual);
        Assertions.assertThat(Strings.isNullOrEmpty(actual)).isEqualTo(true);
    }

    public static void verifyResponseValueNotNullOrEmpty(String message, String actual) {
        LoggerUtils.info(message + " actual is " + actual);
        Assertions.assertThat(Strings.isNullOrEmpty(actual)).isEqualTo(false);
    }

    public static void verifyResponseValueEqual(String message, String actual, String expected) {
        LoggerUtils.verifyResponse(message + " actual is " + actual + ", expected is " + expected);
        Assertions.assertThat(actual).isEqualTo(expected);
    }

    public static void verifyResponseValueEqual(String message, boolean actual, boolean expected) {
        LoggerUtils.verifyResponse(message + " actual is " + actual + ", expected is " + expected);
        Assertions.assertThat(actual).isEqualTo(expected);
    }

    public static String getResponseValueByKey(Response response, String keyPath) {
        try {
            if (response.contentType().contains("json")) {
                return response.jsonPath().get(keyPath) + "";
            } else return response.xmlPath().get(keyPath) + "";
        } catch (Exception e) {
            throw new RuntimeException("Cannot find value with key path: " + keyPath + "\n" + e.getMessage());
        }
    }
}
