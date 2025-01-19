package com.auto.web.pages;

import com.auto.web.common.BasePage;

public class CommonPage extends BasePage {

    public void refreshPage() {
        getDriver().navigate().refresh();
    }
}
