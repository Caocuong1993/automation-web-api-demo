package com.auto.web.steps;

import com.auto.web.pages.HomePage;
import com.auto.utils.DateTime;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.steps.ScenarioSteps;

import java.util.Locale;

public class HomeSteps extends ScenarioSteps {
    HomePage homePage;

    @Step
    public void userInputLocationToSearch(String location) {
        homePage.userInputLocationToSearch(location);
    }

    @Step
    public void userClickSearchButton() {
        homePage.userClickSearchButton();
    }

    @Step
    public void userChooseItem(String location) {
        homePage.userChooseItem(location);
    }

    @Step
    public void verifyCityNameIsDisplayedCorrectly(String location) {
        homePage.verifyTextValueEquals(homePage.getCityName(), location);
    }

    @Step
    public void verifyCurrentDateIsDisplayedCorrectly() {
        String expectedCurrentDate = DateTime.convertDateWithFormat("America/Los_Angeles", "MMM dd, hh:mma");
        homePage.verifyTextValueEquals(homePage.getCurrentDate().toLowerCase(Locale.ROOT), expectedCurrentDate.toLowerCase(Locale.ROOT));
    }

    @Step
    public void verifyTemperatureIsDisplayedCorrectly() {
        homePage.verifyTextValueEquals(homePage.isTemperatureIsDisplayedCorrectly(), true);
    }
}
