package com.auto.api.steps;

import com.auto.api.common.CommonUtils;
import com.auto.api.common.Constants;
import com.auto.api.common.HeaderRequest;
import com.auto.api.common.RestAssuredService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import net.thucydides.core.annotations.Step;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.Instant;
import java.util.List;
import java.util.Map;

public class CommonAPISteps extends CommonUtils {

    @Step
    public void callAPIGetAllRepositories() {
        HeaderRequest headerRequest = new HeaderRequest();
        Map<String, String> headerMapRequest = headerRequest.headerMap();
        RestAssuredService.callAPIGet(Constants.CONFIG_SERVICE_ENDPOINT, Constants.API_GET_REPOSITORIES, "", headerMapRequest);
    }

    @Step
    public String getTotalOpenIssues(Response response) {
        JSONArray repos = new JSONArray(response.getBody().asString());
        int totalOpenIssues = 0;
        for (int i = 0; i < repos.length(); i++) {
            JSONObject object = repos.getJSONObject(i);
            totalOpenIssues += Integer.parseInt(object.get("open_issues").toString());
        }
        return String.valueOf(totalOpenIssues);
    }

    @Step
    public String getRepoMostWatchers(Response response) {
        JSONArray repos = new JSONArray(response.getBody().asString());
        int maxWatchers = 0;
        String repoMostWatcher = "";
        for (int i = 0; i < repos.length(); i++) {
            JSONObject object = repos.getJSONObject(i);
            int mostWatchers = Integer.parseInt(object.get("watchers").toString());
            if (mostWatchers > maxWatchers) {
                maxWatchers = mostWatchers;
                repoMostWatcher = object.get("url").toString();
            }
        }

        return repoMostWatcher;
    }

    @Step
    public String sortRepoDescendingByDateUpdate(Response response) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        // Convert JSON to List of Maps
        List<Map<String, Object>> repoList = objectMapper.readValue(response.getBody().asString(), new TypeReference<>() {
        });
        // Sort List by "updated_at" in descending order
        repoList.sort((map1, map2) -> Instant.parse((String) map2.get("updated_at")).compareTo(Instant.parse((String) map1.get("updated_at"))));
        return objectMapper.writeValueAsString(repoList);
    }


}
