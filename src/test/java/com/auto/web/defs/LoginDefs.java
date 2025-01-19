package com.auto.web.defs;

import com.auto.web.steps.LoginSteps;
import cucumber.api.java.en.Given;
import net.thucydides.core.annotations.Steps;

public class LoginDefs {

    @Steps
    LoginSteps loginSteps;

    @Given("^open web app$")
    public void openWebApp() {
        loginSteps.openWebApp();
    }

}