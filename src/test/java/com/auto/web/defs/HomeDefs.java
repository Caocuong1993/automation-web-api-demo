package com.auto.web.defs;

import com.auto.web.steps.HomeSteps;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import net.thucydides.core.annotations.Steps;

public class HomeDefs {

    @Steps
    HomeSteps homeSteps;

    @Given("^user input location want to search: \"([^\"]*)\"$")
    public void userInputLocationToSearch(String location) {
        homeSteps.userInputLocationToSearch(location);
    }

    @Given("^user choose item: \"([^\"]*)\"$")
    public void userChooseItem(String location) {
        homeSteps.userChooseItem(location);
    }

    @When("^user click search button$")
    public void userClickSearchButton() {
        homeSteps.userClickSearchButton();
    }

    @When("^user verify the city name: \"([^\"]*)\" is displayed correctly$")
    public void verifyCityNameIsDisplayedCorrectly(String location) {
        homeSteps.verifyCityNameIsDisplayedCorrectly(location);
    }

    @When("^user verify the current date is displayed correctly$")
    public void verifyCurrentDateIsDisplayedCorrectly() {
        homeSteps.verifyCurrentDateIsDisplayedCorrectly();
    }

    @When("^user verify the temperature is displayed correctly and it is a number$")
    public void verifyTemperatureIsDisplayedCorrectly() {
        homeSteps.verifyTemperatureIsDisplayedCorrectly();
    }


}