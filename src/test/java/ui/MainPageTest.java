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
        page.open();

        boolean result = page.demosDropdownNavigation(itemText, expectedUrlPart);
        assertTrue(result, "DEMOS dropdown navigation failed for: " + itemText);
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
        page.open();

        boolean result = page.menuButtonNavigation(buttonText, expectedUrlPart);
        assertTrue(result, "Menu button navigation failed for: " + buttonText);
    }
}