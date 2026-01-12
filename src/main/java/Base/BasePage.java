package Base;

import driver.DriverManager;
import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;

    // ---------------- Constructor ----------------
    public BasePage() {
        this.driver = DriverManager.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // ---------------- Basic element actions ----------------
    @Step("Click on element: {locator}")
    protected void click(By locator) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        scrollIntoView(element);
        highlightElement(element);
        element.click();
    }

    @Step("Type '{text}' into element: {locator}")
    protected void type(By locator, String text) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        scrollIntoView(element);
        highlightElement(element);
        element.clear();
        element.sendKeys(text);
    }

    @Step("Get text from element: {locator}")
    protected String getText(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).getText();
    }

    @Step("Get attribute '{attribute}' from element: {locator}")
    protected String getAttribute(By locator, String attribute) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).getAttribute(attribute);
    }

    @Step("Check if element is displayed: {locator}")
    protected boolean isDisplayed(By locator) {
        try {
            return driver.findElement(locator).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    // ---------------- Scroll / JS Actions ----------------
    @Step("Scroll element into view")
    protected void scrollIntoView(WebElement element) {
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'center'});", element);
    }

    @Step("Click element with JS")
    protected void clickWithJS(By locator) {
        WebElement element = driver.findElement(locator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    @Step("Highlight element")
    protected void highlightElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].style.border='3px solid red';" +
                        "setTimeout(function(){ arguments[0].style.border=''; }, 500);", element);
    }

    // ---------------- Checkbox / Radio ----------------
    @Step("Check checkbox")
    protected void checkCheckbox(By locator) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        if (!element.isSelected()) element.click();
    }

    @Step("Uncheck checkbox")
    protected void uncheckCheckbox(By locator) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        if (element.isSelected()) element.click();
    }

    // ---------------- Dropdown / Select ----------------
    @Step("Select dropdown by visible text")
    protected void selectByVisibleText(By locator, String text) {
        Select select = new Select(wait.until(ExpectedConditions.elementToBeClickable(locator)));
        select.selectByVisibleText(text);
    }

    @Step("Select dropdown by value")
    protected void selectByValue(By locator, String value) {
        Select select = new Select(wait.until(ExpectedConditions.elementToBeClickable(locator)));
        select.selectByValue(value);
    }

    @Step("Select dropdown by index")
    protected void selectByIndex(By locator, int index) {
        Select select = new Select(wait.until(ExpectedConditions.elementToBeClickable(locator)));
        select.selectByIndex(index);
    }

    @Step("Get all dropdown options")
    protected List<String> getAllDropdownOptions(By locator) {
        Select select = new Select(wait.until(ExpectedConditions.visibilityOfElementLocated(locator)));
        List<String> options = new ArrayList<>();
        for (WebElement option : select.getOptions()) {
            options.add(option.getText());
        }
        return options;
    }

    // ---------------- Alert / Frame / Window ----------------
    protected void switchToFrame(By locator) {
        WebElement frame = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        driver.switchTo().frame(frame);
    }

    protected void switchToDefault() {
        driver.switchTo().defaultContent();
    }

    protected void acceptAlert() {
        wait.until(ExpectedConditions.alertIsPresent()).accept();
    }

    protected void dismissAlert() {
        wait.until(ExpectedConditions.alertIsPresent()).dismiss();
    }

    protected String getAlertText() {
        return wait.until(ExpectedConditions.alertIsPresent()).getText();
    }

    @Step("Switch to last window")
    protected void switchToLastWindow() {
        Set<String> handles = driver.getWindowHandles();
        driver.switchTo().window(handles.toArray(new String[0])[handles.size() - 1]);
    }

    @Step("Switch to newly opened window")
    protected void switchToNewlyOpenedWindow(String originalWindow) {
        Set<String> handles = driver.getWindowHandles();
        for (String handle : handles) {
            if (!handle.equals(originalWindow)) {
                driver.switchTo().window(handle);
                break;
            }
        }
    }
}