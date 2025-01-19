package com.auto.utils;

import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;

import java.io.File;

public class DataUtils {
    public static final String PATH_RESOURCE;
    public static final String PATH_RESOURCE_DATA;
    public static final EnvironmentVariables ENV;

    public DataUtils() {
    }

    public static String getValueConf(String config) {
        return EnvironmentSpecificConfiguration.from(ENV).getProperty(config);
    }

    static {
        PATH_RESOURCE = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" + File.separator + "resources";
        PATH_RESOURCE_DATA = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + "data";
        ENV = SystemEnvironmentVariables.createEnvironmentVariables();
    }
}
