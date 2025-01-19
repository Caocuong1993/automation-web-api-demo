package com.auto.web.steps;

import com.auto.web.pages.LoginPage;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.steps.ScenarioSteps;

public class LoginSteps extends ScenarioSteps {
    LoginPage loginPage;

    @Step
    public void openWebApp() {
        loginPage.openWebApp();
    }

}
