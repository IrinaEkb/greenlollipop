package pages;

import Base.BasePage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;

public class DropDownMainPagePE extends BasePage {

    private static final String PAGE_URL = "https://practice.expandtesting.com";
    private final By popupCloseButton = By.xpath("//button[span[text()='Opt Out']]");
    private final By dropdown = By.id("examples-dropdown");
    private final By dropdownItems = By.cssSelector("ul.dropdown-menu.show li a.dropdown-item");

    public DropDownMainPagePE(WebDriver driver) {
        super(driver);
    }

    public void open() {
        driver.get(PAGE_URL);
        handlePopup();
    }
    public void handlePopup() {
        try {
            WebElement closeBtn = wait.until(ExpectedConditions.elementToBeClickable(popupCloseButton));
            highlightElement(closeBtn);
            closeBtn.click();
            System.out.println("Popup closed");
        } catch (TimeoutException e) {
            System.out.println("Popup not found, continue test"); }
    }

    public List<WebElement> getAllDropdownItems() {
        WebElement dropdownElement = wait.until(ExpectedConditions.visibilityOfElementLocated(dropdown));
        highlightElement(dropdownElement);
        dropdownElement.click();
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(dropdownItems));
    }

    public List<String> getDropdownItemTexts() {
        List<WebElement> items = getAllDropdownItems();
        List<String> texts = new ArrayList<>();
        for (WebElement item : items) {
            highlightElement(item);
            texts.add(item.getText());
        }
        return texts;
    }
}

