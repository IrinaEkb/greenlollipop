package ui;

import Base.BaseTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.DropDownMainPagePE;

import java.util.List;

public class DropDownMainPagePEtest extends BaseTest {

    @DataProvider(name = "dropdownItemsProvider")
    public Object[][] dropdownItemsProvider() {
        return new Object[][]{
                {"Examples"},
                {"Apps"},
                {"APIs"},
                {"Assertions"},
                {"Reports"}
        };
    }

    @Test(dataProvider = "dropdownItemsProvider", groups = {"smoke"})
    public void dropdownItemsTest(String expectedItem) {
        DropDownMainPagePE page = new DropDownMainPagePE(getDriver());
        page.open(); // открываем страницу один раз для этого теста

        List<String> actualItems = page.getDropdownItemTexts();
        boolean found = false;

        for (String item : actualItems) {
            if (item.equalsIgnoreCase(expectedItem)) {
                found = true;
                break;
            }
        }

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(found, "Expected dropdown item '" + expectedItem + "' not found!");
        softAssert.assertAll();
    }
}