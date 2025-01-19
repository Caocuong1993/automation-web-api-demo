package com.auto.web.defs;

import com.auto.utils.LoggerUtils;
import com.auto.web.steps.CommonSteps;
import cucumber.api.java.After;
import net.serenitybdd.core.Serenity;
import net.thucydides.core.annotations.Steps;

public class CommonDefs {
    @Steps
    CommonSteps commonSteps;

    @After
    public void afterScenario() {
        commonSteps.pages().getDriver().close();
        commonSteps.pages().getDriver().quit();
        try {
            Runtime.getRuntime().exec("killall Dock");
        } catch (Exception e) {
            LoggerUtils.error(e.toString());
        }
    }

}


