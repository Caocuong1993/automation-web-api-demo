package com.auto.web.steps;


import com.auto.web.pages.CommonPage;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.steps.ScenarioSteps;


public class CommonSteps extends ScenarioSteps {
    CommonPage commonPage;

    @Step
    public void switchToBrowserTabByIndex(int index) {
        commonPage.switchToBrowserTabByIndex(index);
    }

}