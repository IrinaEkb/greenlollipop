package ui;

import Base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.ExcelUtils;

public class UserFormExcelTest extends BaseTest {

    @DataProvider(name = "userData")
    public Object[][] getUserData() {
        return ExcelUtils.getTestData(
                "src/test/resources/testdata/MOCK_DATA.xlsx",
                "data"
        );
    }

    @Test(dataProvider = "userData")
    public void userDataDrivenTest(
            String id,
            String firstName,
            String lastName,
            String email,
            String gender,
            String ipAddress
    ) {
        System.out.println("User: " + firstName + " " + lastName);
        System.out.println("Email: " + email + " | Gender: " + gender + " | IP: " + ipAddress);

        Assert.assertFalse(firstName.isEmpty(), "First name should not be empty");
        Assert.assertFalse(lastName.isEmpty(), "Last name should not be empty");
        Assert.assertFalse(email.isEmpty(), "Email should not be empty");
        Assert.assertFalse(gender.isEmpty(), "Gender should not be empty");
    }
}