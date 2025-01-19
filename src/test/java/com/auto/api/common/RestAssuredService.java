package com.auto.api.common;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import net.serenitybdd.core.Serenity;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class RestAssuredService {

    public static void callApiPost(String url, String endpoint, String mapBody, Map<String, String> mapHeader) {
        RestAssured.baseURI = url;
        RestAssured.basePath = endpoint;
        String beautyRequest = JsonUtils.beautyObjectToJSon(mapBody);
        Response response = given().headers(mapHeader).body(mapBody).log().all().post().then().extract().response().prettyPeek();
        saveResponseToSession(response);
        try {
            Serenity.recordReportData().withTitle("URL").andContents(url);
            Serenity.recordReportData().withTitle("Endpoint").andContents(endpoint);
            Serenity.recordReportData().withTitle("Request Body").andContents(beautyRequest);
            Serenity.recordReportData().withTitle("Response Data").andContents(response.asString());
        } catch (NullPointerException ignored) {
        }
    }

    public static void callApiGet(String url, String endpoint, String mapBody, Map<String, String> mapHeader) {
        RestAssured.baseURI = url;
        RestAssured.basePath = endpoint;
        String beautyRequest = JsonUtils.beautyObjectToJSon(mapBody);
        Response response = given().relaxedHTTPSValidation().headers(mapHeader).body(mapBody).log().all().get().then().extract().response().prettyPeek();
        saveResponseToSession(response);
        try {
            Serenity.recordReportData().withTitle("URL").andContents(url);
            Serenity.recordReportData().withTitle("Endpoint").andContents(endpoint);
            Serenity.recordReportData().withTitle("Request Body").andContents(beautyRequest);
            Serenity.recordReportData().withTitle("Response Data").andContents(response.asString());
        } catch (NullPointerException ignored) {
        }
    }

    public static void callAPIPost(String nameProperties, String keyConfig, String mapBody, Map<String, String> mapHeader) {
        String url = PropertiesManager.getBaseUriFromConfig(Constants.CONFIG_SERVICE_PROPERTIES);
        String endpoint = PropertiesManager.getEndpointFromConfig(nameProperties, keyConfig);
        callApiPost(url, endpoint, mapBody, mapHeader);

    }

    public static void callAPIGet(String nameProperties, String keyConfig, String mapBody, Map<String, String> mapHeader) {
        String url = PropertiesManager.getBaseUriFromConfig(Constants.CONFIG_SERVICE_PROPERTIES);
        String endpoint = PropertiesManager.getEndpointFromConfig(nameProperties, keyConfig);
        callApiGet(url, endpoint, mapBody, mapHeader);

    }

    private static void saveResponseToSession(Response response) {
        Serenity.setSessionVariable("rest").to(response);
    }
}
