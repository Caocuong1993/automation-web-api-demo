package com.auto.api.common;


import com.auto.utils.DataUtils;
import com.auto.utils.Utilities;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertiesManager {

    private PropertiesManager() {
        throw new IllegalStateException("Utility class");
    }

    private static String getNameConfigProperties(String keyConfig) {
        return DataUtils.getValueConf(keyConfig);
    }

    public static String getBaseUriFromConfig(String nameProperties) {
        String keyConfig = "base-uri-" + Utilities.ENVIRONMENT;
        Map<String, String> configProperties = getDataPropertiesFile(nameProperties);
        return configProperties.get(keyConfig);
    }

    public static String getBaseTokenUriFromConfig(String nameProperties) {
        String keyConfig = "base-token-" + Utilities.ENVIRONMENT;
        Map<String, String> configProperties = getDataPropertiesFile(nameProperties);
        return configProperties.get(keyConfig);
    }

    public static String getEndpointFromConfig(String nameProperties, String keyValue) {
        Map<String, String> configProperties = getDataPropertiesFile(nameProperties);
        return configProperties.get(keyValue);
    }

    public static Map<String, String> getDataPropertiesFile(String nameConfig) {
        Properties prop = new Properties();
        Map<String, String> map = new HashMap<>();
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream("src/test/resources/" + nameConfig);
            prop.load(inputStream);
            for (String key : prop.stringPropertyNames()) {
                String value = prop.getProperty(key);
                map.put(key, value);
            }
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();

            System.out.println("Some issue finding or loading file....!!! " + e.getMessage());
        }
        return map;
    }

}
