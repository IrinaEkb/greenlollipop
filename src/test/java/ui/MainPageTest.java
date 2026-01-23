package ui;

import Base.BaseTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.MainPage;

import static org.testng.Assert.assertTrue;

public class MainPageTest extends BaseTest {

    @DataProvider(name = "demosDropdownData")
    public Object[][] demosDropdownData() {
        return new Object[][]{
                {"Examples", "#examples"},
                {"Apps", "#apps"},
                {"APIs", "#api"},
                {"Assertions", "#assertions"},
                {"Reports", "#reports"}
        };
    }

    @Test(dataProvider = "demosDropdownData", groups = {"ui", "regression", "smoke"})
    public void demosDropdownTest(String itemText, String expectedUrlPart) {
        MainPage page = new MainPage();

        log.info("Opening Main page");
        page.open();

        log.info("Verifying that Main page is opened");
        assertTrue(page.isPageOpened(), "Main page did not open");
        log.info("Main page opened successfully");

        log.info("Navigating through DEMOS dropdown: {}", itemText);
        boolean result = page.demosDropdownNavigation(itemText, expectedUrlPart);

        log.info("Verifying navigation result for DEMOS dropdown item: {}", itemText);
        assertTrue(result, "DEMOS dropdown navigation failed for: " + itemText);
        log.info("DEMOS dropdown navigation passed for: {}", itemText);
    }

    @DataProvider(name = "menuButtonsData")
    public Object[][] menuButtonsData() {
        return new Object[][]{
                {"Tools", "#tools"},
                {"Tips", "/tips"},
                {"Test Cases", "/test-cases"},
                {"API Testing", "/notes/api/api-docs"},
                {"About", "/about"}
        };
    }

    @Test(dataProvider = "menuButtonsData", groups = {"ui", "regression", "smoke"})
    public void mainMenuButtonsTest(String buttonText, String expectedUrlPart) {
        MainPage page = new MainPage();

        log.info("Opening Main page");
        page.open();

        log.info("Verifying that Main page is opened");
        assertTrue(page.isPageOpened(), "Main page did not open");
        log.info("Main page opened successfully");

        log.info("Clicking on main menu button: {}", buttonText);
        boolean result = page.menuButtonNavigation(buttonText, expectedUrlPart);

        log.info("Verifying navigation result for menu button: {}", buttonText);
        assertTrue(result, "Menu button navigation failed for: " + buttonText);
        log.info("Menu button navigation passed for: {}", buttonText);
    }
}