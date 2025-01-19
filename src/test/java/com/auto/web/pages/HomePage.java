package com.auto.web.pages;

import com.auto.web.common.BasePage;

public class HomePage extends BasePage {
    private final String LBL_SEARCH = "//input[@placeholder='Search city']";
    private final String BTN_SEARCH = "//button[@type='submit']";
    private final String BTN_ITEM = "//span[text()[normalize-space() = '%s']]";
    private final String LBL_DATETIME = "(//div[contains(@class,'current-container')]//div//span)[1]";
    private final String LBL_NAME_CITY = "//div[contains(@class,'current-container')]//div//h2";
    private final String LBL_TEMPERATURE = "//div[contains(@class,'current-container')]//div//span[@class='heading']";

    public void userInputLocationToSearch(String location) {
        waitAndType(LBL_SEARCH, location);
    }

    public void userClickSearchButton() {
        clickOnElement(BTN_SEARCH);
    }

    public void userChooseItem(String location) {
        String xpath = String.format(BTN_ITEM, location);
        clickOnElement(xpath);
    }

    public String getCityName() {
        return getText(LBL_NAME_CITY).trim();
    }

    public String getCurrentDate() {
        return getText(LBL_DATETIME).trim();
    }

    public boolean isTemperatureIsDisplayedCorrectly() {
        String temp = getText(LBL_TEMPERATURE);
        String regex = "\\d+°C";
        return temp.matches(regex);
    }

}
