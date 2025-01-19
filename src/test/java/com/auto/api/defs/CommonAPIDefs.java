package com.auto.api.defs;

import com.auto.api.common.CommonUtils;
import com.auto.api.steps.CommonAPISteps;
import com.fasterxml.jackson.core.JsonProcessingException;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.response.Response;
import net.serenitybdd.core.Serenity;
import net.thucydides.core.annotations.Steps;

public class CommonAPIDefs {
    @Steps
    CommonAPISteps commonAPISteps;

    @When("^user call api get all repositories$")
    public void callAPIGetAllRepositories() {
        commonAPISteps.callAPIGetAllRepositories();
    }

    @Then("^user verify API response code display equals (.*)$")
    public void verifyResponseCode(int responsesCode) {
        Response rest = Serenity.sessionVariableCalled("rest");
        CommonUtils.verifyResponseStatus(rest, responsesCode);
    }

    @Then("^user get total open issues are there across all repositories$")
    public void getTotalOpenIssues() {
        Response rest = Serenity.sessionVariableCalled("rest");
        String totalIssues = commonAPISteps.getTotalOpenIssues(rest);
        try {
            Serenity.recordReportData().withTitle("Total open issues").andContents(totalIssues);
        } catch (NullPointerException ignored) {
        }
    }

    @Then("^user get repository has the most watchers$")
    public void getRepoMostWatchers() {
        Response rest = Serenity.sessionVariableCalled("rest");
        String repo = commonAPISteps.getRepoMostWatchers(rest);
        try {
            Serenity.recordReportData().withTitle("Repository has the most watchers").andContents(repo);
        } catch (NullPointerException ignored) {
        }
    }

    @Then("^user sort the repositories by date updated in descending order$")
    public void sortRepoDescendingByDateUpdate() throws JsonProcessingException {
        Response rest = Serenity.sessionVariableCalled("rest");
        String repo = commonAPISteps.sortRepoDescendingByDateUpdate(rest);
        try {
            Serenity.recordReportData().withTitle("Repositories by date updated in descending order").andContents(repo);
        } catch (NullPointerException ignored) {
        }
    }

}
