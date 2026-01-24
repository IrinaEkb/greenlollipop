package utils;

import Base.BaseTest;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {

    @Override
    public void onStart(ITestContext context) {
        System.out.println("Listener started");
    }
    public void onTestFailure(ITestResult result) {
        Object testInstance = result.getInstance();

        if (testInstance instanceof BaseTest) {
            WebDriver driver = ((BaseTest) testInstance).getDriver();

            if (driver != null) {
                try {
                    String testName = result.getMethod().getMethodName();
                    ScreenshotUtils.captureScreenshot(driver, testName);
                } catch (Exception e) {
                    System.out.println("Screenshot failed: " + e.getMessage());
                    e.printStackTrace();
                }
            } else {
                System.out.println("Driver is null, cannot take screenshot");
            }
        }
    }
}