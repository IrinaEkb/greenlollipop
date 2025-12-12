package utils;

import Base.BaseTest;
import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.lang.reflect.Field;

public class TestListener implements ITestListener {
    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println("Test failed: " + result.getName());

        // Attempt to get WebDriver from the test instance (the test class should extend BaseTest)
        Object testInstance = result.getInstance();
        try {
            if (testInstance instanceof BaseTest) {
                WebDriver driver = ((BaseTest) testInstance).getDriver();
                if (driver != null) {
                    ScreenshotUtils.captureScreenshot(driver, result.getMethod().getMethodName() + "_FAIL");
                }
            } else {
                // fallback: try to find a WebDriver field via reflection (optional)
                try {
                    Field driverField = testInstance.getClass().getDeclaredField("driver");
                    driverField.setAccessible(true);
                    Object threadLocal = driverField.get(testInstance); // this is ThreadLocal<WebDriver>
                    if (threadLocal instanceof ThreadLocal) {
                        @SuppressWarnings("unchecked")
                        ThreadLocal<WebDriver> tl = (ThreadLocal<WebDriver>) threadLocal;
                        WebDriver driver = tl.get();
                        if (driver != null) {
                            ScreenshotUtils.captureScreenshot(driver, result.getMethod().getMethodName() + "_FAIL");
                        }
                    }
                } catch (NoSuchFieldException | IllegalAccessException ignored) {
                    // ignore if reflection fails
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
