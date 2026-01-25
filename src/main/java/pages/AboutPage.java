package pages;

import Base.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.EnvConfig;

public class AboutPage extends BasePage {

    private final By testAutomationButton =
            By.xpath("//a[contains(@class,'google-anno')][.//span[normalize-space()='test automation']]");

    private final By drawer = By.id("hd-drawer");
    private final By drawerIframe = By.cssSelector("#hd-drawer iframe");
    private final By drawerCloseButton = By.id("hd-close-button");

    private final String PAGE_PATH = "/about";

    @Step("Open About page")
    public void open() {
        driver.get(EnvConfig.getBaseUrl() + PAGE_PATH);
    }

    @Step("Close popups and banners")
    public void closeAllPopups() {
        try {
            ((JavascriptExecutor) driver).executeScript(
                    "document.querySelectorAll('svg[style*=\"bottom: 0px\"]').forEach(e=>e.remove());");
        } catch (Exception ignored) {}
    }

    @Step("Scroll to 'test automation' button")
    public void scrollToTestAutomation() {
        scrollIntoView(testAutomationButton);
    }

    @Step("Click test automation button")
    public void clickTestAutomation() {
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(testAutomationButton));

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].style.border='3px solid red !important';", btn);

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", btn);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Step("Check if drawer is opened (safe)")
    public boolean isDrawerOpenedSafe() {
        return isDisplayed(drawer);
    }

    @Step("Check if iframe in drawer (safe)")
    public boolean isIframePresentSafe() {
        return isDisplayed(drawerIframe);
    }

    @Step("Close drawer")
    public void closeDrawer() {
        if (isDisplayed(drawerCloseButton)) {
            click(drawerCloseButton);
        }
    }
}