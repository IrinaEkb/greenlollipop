package tests;

import Base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.AboutPage;

public class AboutPageTest extends BaseTest {

    @Test(description = "Verify Website testing services iframe opens on About page")
    public void verifyWebsiteTestingServicesIframe() {

        AboutPage aboutPage = new AboutPage();

        log.info("Opening About page");
        aboutPage.open();

        log.info("Clicking Website testing services button");
        aboutPage.clickWebsiteTestingServices();

        log.info("Validating iframe is opened");
        Assert.assertTrue(
                aboutPage.isServicesIframeOpened(),
                "Website testing services iframe was NOT opened"
        );
    }
}