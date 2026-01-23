package pages;

import Base.BasePage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainPage extends BasePage {

    private static final String PAGE_URL = "https://practice.expandtesting.com";

    // Dropdown
    private final By demosDropdown = By.id("examples-dropdown");
    private final By demosDropdownItems = By.cssSelector("ul.dropdown-menu.show li a.dropdown-item");

    // Menu buttons
    private final By toolsButton = By.cssSelector("a.nav-link[href='/#tools']");
    private final By tipsButton = By.cssSelector("a.nav-link[alt='Automation Tips']");
    private final By testCasesButton = By.cssSelector("a.nav-link[href='/test-cases']");
    private final By apiTestingButton = By.cssSelector("a.nav-link[href='/notes/api/api-docs/']");
    private final By aboutButton = By.cssSelector("a.nav-link[href='/about']");

    private final By popupCloseButton = By.xpath("//button[span[text()='Opt Out']]");

    private final Map<String, By> menuButtons = new HashMap<>();

    public MainPage() {
        menuButtons.put("Tools", toolsButton);
        menuButtons.put("Tips", tipsButton);
        menuButtons.put("Test Cases", testCasesButton);
        menuButtons.put("API Testing", apiTestingButton);
        menuButtons.put("About", aboutButton);
    }

    public void open() {
        driver.get(PAGE_URL);
        closePopupIfPresent();
    }

    public boolean isPageOpened() {
        return isDisplayed(demosDropdown);
    }


    private void closePopupIfPresent() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(popupCloseButton)).click();
        } catch (TimeoutException | NoSuchElementException ignored) {}
    }

    public void highlightElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].style.border='3px solid red'", element
        );
    }

    private boolean isNot404() {

        return !driver.getPageSource().contains("404");
    }

    private void openDemosDropdown() {
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(demosDropdown));
        highlightElement(dropdown);
        dropdown.click();
    }

    public WebElement getWebElementForDropdownItem(String itemText) {
        openDemosDropdown();
        List<WebElement> items = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(demosDropdownItems));
        for (WebElement item : items) {
            if (item.getText().trim().equalsIgnoreCase(itemText)) {
                highlightElement(item); // подсветка красным
                return item;
            }
        }
        throw new NoSuchElementException("Dropdown item not found: " + itemText);
    }

    public boolean demosDropdownNavigation(String itemText, String expectedUrlPart) {
        WebElement item = getWebElementForDropdownItem(itemText);
        item.click();
        String currentUrl = driver.getCurrentUrl();
        return currentUrl.contains(expectedUrlPart) && isNot404();
    }

    private By getButtonLocator(String buttonText) {
        By locator = menuButtons.get(buttonText);
        if (locator == null) throw new NoSuchElementException("Button not found: " + buttonText);
        return locator;
    }

    public WebElement getWebElementForMenuButton(String buttonText) {
        By locator = getButtonLocator(buttonText);
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        highlightElement(element); // подсветка красным
        return element;
    }

    public boolean menuButtonNavigation(String buttonText, String expectedUrlPart) {

        System.out.println("\n==============================");
        System.out.println("Testing menu button: " + buttonText);

        String urlBefore = driver.getCurrentUrl();
        System.out.println("URL BEFORE click: " + urlBefore);

        WebElement element = getWebElementForMenuButton(buttonText);

        try {
            element.click();
            System.out.println("Clicked on button: " + buttonText);
        } catch (Exception e) {
            System.out.println("❌ CLICK FAILED for button: " + buttonText);
            e.printStackTrace();
            return false;
        }

        try {
            wait.until(ExpectedConditions.urlContains(expectedUrlPart));
            System.out.println("URL contains expected part: " + expectedUrlPart);
        } catch (TimeoutException e) {
            System.out.println("❌ URL did NOT change as expected");
        }

        String urlAfter = driver.getCurrentUrl();
        System.out.println("URL AFTER click: " + urlAfter);

        boolean urlChanged = !urlBefore.equals(urlAfter);
        boolean containsExpected;

        if (buttonText.equals("Tips")) {
            containsExpected = urlAfter.contains(expectedUrlPart) || urlAfter.contains("#google_vignette");
        } else {
            containsExpected = urlAfter.contains(expectedUrlPart);
        }

        boolean not404 = isNot404();

        System.out.println("URL changed: " + urlChanged);
        System.out.println("Contains expected part: " + containsExpected);
        System.out.println("Not 404 page: " + not404);

        boolean result = urlChanged && containsExpected && not404;
        System.out.println("RESULT for " + buttonText + ": " + result);
        System.out.println("==============================\n");

        return result;
    }

    public List<String> getDemosDropdownItemsText() {
        openDemosDropdown();
        List<WebElement> items = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(demosDropdownItems));
        List<String> texts = new ArrayList<>();
        for (WebElement item : items) {
            highlightElement(item); // подсветка красным
            texts.add(item.getText().trim());
        }
        return texts;
    }
}