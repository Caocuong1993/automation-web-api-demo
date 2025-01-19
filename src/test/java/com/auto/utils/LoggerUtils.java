package com.auto.utils;

import io.restassured.path.json.JsonPath;
import org.json.JSONObject;

public class LoggerUtils {
    private static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    private static final String ANSI_RESET = "\u001B[0m";
    public static String QTEST_NOTE = "";

    public static void endpoint(String endpoint) {
        System.out.println(ANSI_GREEN_BACKGROUND + "Endpoint:" + ANSI_RESET);
        System.out.println(endpoint);
        QTEST_NOTE += "\n" + "URL: " + "\n" + endpoint + "\n";
    }

    public static void body(String body) {
        System.out.println(ANSI_GREEN_BACKGROUND + "Body:" + ANSI_RESET);
        System.out.println(body);
        QTEST_NOTE += "\n" + "Body: " + "\n" + body + "\n";
    }

    public static void response(JsonPath response) {
        System.out.println(ANSI_GREEN_BACKGROUND + "Response:" + ANSI_RESET);
        System.out.println(response.prettify());
        QTEST_NOTE += "\n" + "Response: " + "\n" + response.prettify() + "\n";
    }

    public static void verifyResponse(String assertMessage) {
        System.out.println(ANSI_GREEN_BACKGROUND + "Verify response:" + ANSI_RESET);
        System.out.println(assertMessage);
    }

    public static void info(String info) {
        System.out.println("[INFO] " + info);
    }

    public static void desc(String desc) {
        System.out.println("Description: " + desc);
    }

    public static void body(JSONObject body) {
        System.out.println("Request Body:");
        System.out.println(body.toString(4));
    }

    public static void error(String error) {
        System.out.println("[ERROR] " + error);
    }

    public static void warn(String msg) {
        System.out.println(">> Warn: |" + msg);
    }

    public static void debug(String msg) {
        System.out.println(">> Debug: | " + msg);
    }

}
