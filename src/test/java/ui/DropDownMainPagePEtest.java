package ui;

import Base.BaseTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.DropDownMainPagePE;

import java.util.List;

public class DropDownMainPagePEtest extends BaseTest {
    private DropDownMainPagePE page;

    @BeforeClass
    public void setupPage() {
        page = new DropDownMainPagePE(getDriver());
        page.open(); // open page once, not many times for each data provider item
    }

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
        page.open();

        List<String> actualItems = page.getDropdownItemTexts();
        boolean found = false;

        System.out.println("Dropdown items on page:");
        for (String item : actualItems) {
            System.out.println("- " + item);
            if (item.equalsIgnoreCase(expectedItem)) {
                found = true;
            }
        }

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(found, "Expected dropdown item '" + expectedItem + "' not found!");

        if (found) {
            System.out.println("Yes, match '" + expectedItem + "' found in dropdown!");
        } else {
            System.out.println("No match '" + expectedItem + "' NOT found in dropdown!");
        }

        softAssert.assertAll();
    }
}