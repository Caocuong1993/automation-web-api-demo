package com.auto.api.common;

import java.io.File;

public class PathUtils {
    public static final String PROJECT_PATH = System.getProperty("user.dir");
    public static final String ROOT_CONFIG_FOLDER = "src/test/resources/";
    public static final String PATH_FILE_JSON_FOLDER_SYSTEMTEST = PROJECT_PATH + File.separator + ROOT_CONFIG_FOLDER;
    public static final String PATH_FILE_DOWNLOAD = PROJECT_PATH + "/" + ROOT_CONFIG_FOLDER + "file/downloads";
}
