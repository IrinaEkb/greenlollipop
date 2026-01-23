package pages;

import Base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class AboutPage extends BasePage {

    private static final String PAGE_URL = "https://practice.expandtesting.com/about";

    private final By websiteTestingServicesButton =
            By.xpath("//div[@role='link' and @aria-label='Website testing services']");

    private final By servicesIframe =
            By.cssSelector("iframe");

    public void open() {

        driver.get(PAGE_URL);
    }

    public void clickWebsiteTestingServices() {
        WebElement button = wait.until(
                ExpectedConditions.elementToBeClickable(websiteTestingServicesButton)
        );
        scrollIntoView(button);
        highlightElement(button);
        button.click();
    }

    public boolean isServicesIframeOpened() {
        try {
            WebElement iframe = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(servicesIframe)
            );
            highlightElement(iframe);
            return iframe.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}