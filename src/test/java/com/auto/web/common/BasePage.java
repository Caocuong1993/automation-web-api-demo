package com.auto.web.common;

import com.auto.utils.LoadConfig;
import com.auto.utils.LoggerUtils;
import io.appium.java_client.functions.ExpectedCondition;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.pages.PageObject;
import org.assertj.core.util.Strings;
import org.joda.time.DateTime;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class BasePage extends PageObject {
    public static final long TIMEOUT_IN_SECONDS = LoadConfig.getWaitTimeoutInSecond();

    // <!---------------------------------------------------- BEGIN COMMON ------------------------------------------------------------>

    public void highlightElement(WebElementFacade element) {
        executeJS("arguments[0].style.border='2px solid red'", element);
    }

    public void highlightElementAndLogText(WebElementFacade element) {
        highlightElement(element);
        String text = element.getText();
        LoggerUtils.info("Click on element " + ((text == null || text.isEmpty() || text.equals(" ")) ? element.toString() : "has text: [" + text + "]"));
    }

    public <T> T executeJS(String script, WebElementFacade element) {
        return (T) ((JavascriptExecutor) getDriver()).executeScript(script, element);
    }

    public boolean isElementExist(String xPath) {
        boolean isExist = false;
        List<WebElementFacade> e = findAllElements(xPath);
        if (!e.isEmpty()) {
            highlightElement(e.get(0));
            isExist = true;
        }
        LoggerUtils.info("Is Element " + xPath + " Exist: " + isExist);
        return isExist;
    }

    public void scrollElementIntoView(WebElementFacade element) {
        executeJS("arguments[0].scrollIntoView(false);", element);
    }

    public WebElementFacade scrollElementIntoView(String xPath) {
        WebElementFacade element = getElement(xPath);
        scrollElementIntoView(element);
        return element;
    }

    public void quitDriver() {
        getDriver().manage().deleteAllCookies();
        getDriver().quit();
    }


    public void clearCacheAndRefreshPage() {
        clearCache();
        getDriver().navigate().refresh();
    }

    public void clearCache() {
        getDriver().manage().deleteAllCookies();
    }

    public void waitUntilElementReady(WebElementFacade element) {
        scrollElementIntoView(element);
        element.waitUntilClickable();
    }

    public void verifyTextNotNullOrEmpty(String expected) {
        boolean actual = Strings.isNullOrEmpty(expected);
        LoggerUtils.info("Verify text is not null/empty, actual is " + actual + ", expected is " + expected);
        assertThat(actual).isEqualTo(false);
    }

    public void verifyElementIsDisplayed(WebElementFacade element, boolean expected) {
        boolean actual = element.isDisplayed();
        LoggerUtils.info("Verify element is displayed, actual is " + actual + ", expected is " + expected);
        assertThat(actual).isEqualTo(expected);
    }

    public void verifyElementIsExisted(String xpath, boolean expected) {
        boolean actual = isElementExist(xpath);
        LoggerUtils.info("Verify element is existed, actual is " + actual + ", expected is " + expected);
        assertThat(actual).isEqualTo(expected);
    }

    public void verifyTextValueEquals(String actual, String expected) {
        LoggerUtils.info("Verify text value equals, actual is " + actual + ", expected is " + expected);
        assertThat(actual).isEqualTo(expected);
    }

    public void verifyTextValueEquals(boolean actual, boolean expected) {
        LoggerUtils.info("Verify value equals, actual is " + actual + ", expected is " + expected);
        assertThat(actual).isEqualTo(expected);
    }

    // <!---------------------------------------------------- END COMMON ------------------------------------------------------------>

    // <!---------------------------------------------------- BEGIN GET ELEMENTS --------------------------------------------------------->
    public By isXpathOrCssSelector(String value) {
        if (value.contains("//")) return By.xpath(value);
        else return By.cssSelector(value);
    }

    public WebElementFacade getElement(String xpathOrCss) {
        return find(isXpathOrCssSelector(xpathOrCss));
    }

    public List<WebElementFacade> findAllElements(String xpathOrCss) {
        return findAll(xpathOrCss);
    }

    // <!---------------------------------------------------- END GET ELEMENTS ------------------------------------------------------------>

    // <!---------------------------------------------------- BEGIN CLICK ELEMENT --------------------------------------------------------->

    public void clickOnElement(WebElementFacade element) throws Exception {
        DateTime timeout = DateTime.now().plusSeconds(Integer.parseInt(String.valueOf(TIMEOUT_IN_SECONDS)));
        do {
            try {
                scrollElementIntoView(element);
                highlightElementAndLogText(element);
                element.click();
                break;
            } catch (Exception exception) {
                LoggerUtils.warn("Retrying click element...");
                if (!DateTime.now().isBefore(timeout)) throw new Exception("Can not click on element", exception);
            }
        } while (DateTime.now().isBefore(timeout));
        waitForAllLoadingCompleted();
    }

    public void clickOnElement(String xpathOrCss) {
        LoggerUtils.info("xpathOrCss: " + xpathOrCss);
        WebElementFacade element = getElement(xpathOrCss);
        try {
            clickOnElement(element);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // <!---------------------------------------------------- END CLICK ELEMENT --------------------------------------------------------->

    // <!---------------------------------------------------- BEGIN GET TEXT --------------------------------------------------------->
    public String getText(String xpathOrCss) {
        WebElementFacade element = getElement(xpathOrCss);
        scrollElementIntoView(element);
        highlightElement(element);
        return element.getText();
    }

    public String getTextValue(String xpathOrCss) {
        WebElementFacade element = getElement(xpathOrCss);
        highlightElement(element);
        return element.getTextValue();
    }

    public String getTextByAttribute(String xpathOrCss, String attribute) {
        WebElementFacade element = getElement(xpathOrCss);
        highlightElement(element);
        return element.getAttribute(attribute);
    }

    // <!---------------------------------------------------- END GET TEXT --------------------------------------------------------->

    // <!---------------------------------------------------- BEGIN IFRAME --------------------------------------------------------->

    public void openNewBrowserTab(String url) {
        ((JavascriptExecutor) getDriver()).executeScript("window.open();");
        switchToNewBrowserTab();
        getDriver().get(url);
    }

    public void switchToNewBrowserTab() {
        int size = getAllBrowserTab().size();
        String siteHandle = ((String) getAllBrowserTab().toArray()[size - 1]);
        getDriver().switchTo().window(siteHandle);
        getDriver().manage().window().maximize();
        LoggerUtils.info("Switch to the latest browser tab: " + (size - 1));
    }

    public void switchToBrowserTabByIndex(int index) {
        String siteHandle = ((String) getAllBrowserTab().toArray()[index]);
        getDriver().switchTo().window(siteHandle);
        LoggerUtils.info("Switch to browser tab with index: " + index);
    }

    public Set<String> getAllBrowserTab() {
        return getDriver().getWindowHandles();
    }

    // <!---------------------------------------------------- END IFRAME --------------------------------------------------------->

    // <!---------------------------------------------------- BEGIN WAIT --------------------------------------------------------->
    public void waitForAllLoadingCompleted() {
        waitUntilJQueryRequestCompleted(TIMEOUT_IN_SECONDS);
        waitForJQueryLoadingCompleted(TIMEOUT_IN_SECONDS);
        waitUntilHTMLReady(TIMEOUT_IN_SECONDS);
        waitForLoadingIndicatorCircle(TIMEOUT_IN_SECONDS);
    }

    public void waitForLoadingIndicatorCircle(long timeoutInSeconds) {
        waitForInvisibilityOfElement("div[data-role='loading-indicator']", timeoutInSeconds);
    }

    public void waitUntilJQueryRequestCompleted(long timeoutInSeconds) {
        new FluentWait<>(getDriver()).withTimeout(Duration.of(timeoutInSeconds, ChronoUnit.SECONDS))
                .withMessage("**** INFO **** JQUERY STILL LOADING FOR OVER" + timeoutInSeconds + "SECONDS.")
                .pollingEvery(Duration.of(300, ChronoUnit.MILLIS)).until((ExpectedCondition<Boolean>) driver -> {
                    try {
                        JavascriptExecutor jsExec = (JavascriptExecutor) driver;
                        return (Boolean) jsExec.executeScript("return jQuery.active === 0");
                    } catch (Exception e) {
                        return true;
                    }
                });
    }

    public void waitForJQueryLoadingCompleted(long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(getDriver(), timeoutInSeconds);
        ExpectedCondition<Boolean> jQueryLoad = driver -> {
            try {
                return ((Long) ((JavascriptExecutor) getDriver()).executeScript("return $.active") == 0);
            } catch (Exception e) {
                // no jQuery present
                return true;
            }
        };
        wait.until(jQueryLoad);
    }

    public void waitUntilHTMLReady(long timeoutInSeconds) {
        new FluentWait<>(getDriver()).withTimeout(Duration.of(timeoutInSeconds, ChronoUnit.SECONDS))
                .pollingEvery(Duration.of(300, ChronoUnit.MILLIS)).until(new ExpectedCondition<Boolean>() {
                    @Override
                    public Boolean apply(WebDriver d) {
                        try {
                            JavascriptExecutor jsExec = (JavascriptExecutor) d;
                            return jsExec.executeScript("return document.readyState").toString().equals("complete");
                        } catch (Exception e) {
                            return true;
                        }
                    }
                });
    }

    public WebElementFacade waitElementToBePresent(String xpathOrCss) {
        this.waitFor(ExpectedConditions.presenceOfElementLocated(isXpathOrCssSelector(xpathOrCss)));
        return getElement(xpathOrCss);
    }

    public void waitForVisibilityOfElement(String xpathOrCss, long timeOutInSeconds) {
        WebDriverWait wait = new WebDriverWait(getDriver(), timeOutInSeconds);
        wait.until(ExpectedConditions.visibilityOfElementLocated(isXpathOrCssSelector(xpathOrCss)));
    }

    public void waitForVisibilityOfElement(String xpathOrCss) {
        waitForVisibilityOfElement(xpathOrCss, TIMEOUT_IN_SECONDS);
    }

    public void waitForInvisibilityOfElement(String xpathOrCss, long timeOutInSecond) {
        WebDriverWait wait = new WebDriverWait(getDriver(), timeOutInSecond);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(isXpathOrCssSelector(xpathOrCss)));
    }

    public void waitForInvisibilityOfElement(String xpathOrCss) {
        waitForInvisibilityOfElement(xpathOrCss, TIMEOUT_IN_SECONDS);
    }

    public void waitAbit(int millisecond) {
        waitABit(millisecond);
    }

    // <!---------------------------------------------------- END WAIT --------------------------------------------------------->

    // <!---------------------------------------------------- BEGIN INPUT FIELD --------------------------------------------------------->
    public void waitTypeAndTab(String xpathOrCss, String value) {
        WebElementFacade element = getElement(xpathOrCss);
        waitUntilElementReady(element);
        element.clear();
        element.typeAndTab(value);
    }

    public void waitAndType(String xpathOrCss, String value) {
        WebElementFacade element = getElement(xpathOrCss);
        waitUntilElementReady(element);
        element.clear();
        element.type(value);
    }

    // <!---------------------------------------------------- END INPUT FIELD --------------------------------------------------------->

}
