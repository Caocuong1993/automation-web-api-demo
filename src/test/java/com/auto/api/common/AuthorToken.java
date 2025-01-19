package com.auto.api.common;

import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import net.serenitybdd.core.Serenity;

import static io.restassured.RestAssured.given;

public class AuthorToken {
    public static String getAuthorToken(String username, String password, String client_id) {
        String endpoint = PropertiesManager.getEndpointFromConfig(Constants.CONFIG_SERVICE_ENDPOINT, Constants.API_AUTHORIZATION);
        RestAssured.baseURI = PropertiesManager.getBaseTokenUriFromConfig(Constants.CONFIG_SERVICE_PROPERTIES);
        RestAssured.basePath = endpoint;
        RestAssured.defaultParser = Parser.JSON;
        Response response = given()
                .contentType("application/x-www-form-urlencoded")
                .auth().preemptive().basic(username, password)
                .formParam("username", username)
                .formParam("password", password)
                .formParam("client_id", client_id)
                .formParam("grant_type", "password")
                .log().all().relaxedHTTPSValidation()
                .post()
                .then()
                .extract()
                .response().prettyPeek();
        try {
            JsonPath responseJson = response.jsonPath();
            Serenity.recordReportData().withTitle("access_token").andContents(responseJson.getString("access_token").toString());
            return responseJson.getString("access_token");
        } catch (NullPointerException e) {
            return null;
        }
    }
}
