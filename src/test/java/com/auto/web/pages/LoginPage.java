package com.auto.web.pages;

import com.auto.utils.LoadConfig;
import com.auto.utils.LoggerUtils;
import com.auto.web.common.BasePage;

public class LoginPage extends BasePage {

    private final String BTN_CLOSE = "//span[@class='close-btn']";
    private final String BTN_DISMISS = "//button[contains(@class,'button--dismiss')]";

    public void openWebApp() {
        waitAbit(1000);
        open();
        LoggerUtils.info("URL: " + LoadConfig.getURL());
        waitForAllLoadingCompleted();
        getDriver().manage().window().maximize();
        if (isElementExist(BTN_CLOSE))
            clickOnElement(BTN_CLOSE);
        if (isElementExist(BTN_DISMISS))
            clickOnElement(BTN_DISMISS);
    }


}
