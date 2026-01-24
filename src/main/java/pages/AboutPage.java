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
            if (isDisplayed(drawer)) {
                click(drawerCloseButton);
            }
        } catch (Exception ignored) {}

        try {
            ((JavascriptExecutor) driver).executeScript(
                    "document.querySelectorAll('svg[style*=\"bottom: 0px\"]').forEach(e=>e.remove());");
        } catch (Exception ignored) {}
    }

    @Step("Scroll to 'test automation' button")
    public void scrollToTestAutomation() {
        WebElement btn = wait.until(ExpectedConditions.visibilityOfElementLocated(testAutomationButton));
        scrollIntoView(btn);
    }

    @Step("Click test automation button")
    public void clickTestAutomation() {
        clickWithJS(testAutomationButton);
    }

    @Step("Check if drawer is opened (safe)")
    public boolean isDrawerOpenedSafe() {
        try {
            return driver.findElement(drawer).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Check if iframe in drawer (safe)")
    public boolean isIframePresentSafe() {
        try {
            return driver.findElement(drawerIframe).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}