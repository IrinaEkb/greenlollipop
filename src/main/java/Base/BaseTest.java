package Base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;
import utils.EnvConfig;
import utils.ScreenshotUtils;

import java.time.Duration;

public class BaseTest {

    // Each test thread gets its own WebDriver instance to safely run tests in parallel.
    protected static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    // Each test thread gets its own ExtentTest node for parallel reporting.
    protected static ThreadLocal<ExtentTest> testNode = new ThreadLocal<>();

    // Global report manager (single instance used to flush final report).
    protected static ExtentReports extent;

    @BeforeSuite(alwaysRun = true)
    @Parameters({"env"})
    public void setupReport(@Optional("qa1") String env) {
        ExtentSparkReporter reporter = new ExtentSparkReporter("reports/ExtentReport_" + System.currentTimeMillis() + "_" + env + ".html");
        extent = new ExtentReports();
        extent.attachReporter(reporter);
    }

    // env is provided from TestNG XML via @Parameters
    @Parameters({"env"})
    @BeforeMethod(alwaysRun = true)
    public void setUp(@Optional("qa1") String env) {
        // Download/setup proper ChromeDriver binary
        WebDriverManager.chromedriver().setup();
        driver.set(new ChromeDriver());

        // Create WebDriver instance for this thread
        driver.set(new ChromeDriver());

        // Basic implicit wait (short) - explicit waits used in BasePage for element-specific waits
        driver.get().manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.get().manage().window().maximize();

        // Navigate to chosen environment from config properties
        driver.get().get(EnvConfig.getUrl(env));
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        // Сделать скриншот при падении
        if (result.getStatus() == ITestResult.FAILURE) {
            String testName = result.getMethod().getMethodName();
            ScreenshotUtils.captureScreenshot(getDriver(), testName + "_FAILURE");
        }
        // Закрываем браузер
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove(); // remove ThreadLocal reference
        }
        if (testNode.get() != null) {
            testNode.remove();
        }
    }

    @AfterSuite(alwaysRun = true)
    public void flushReport() {
        if (extent != null) {
            extent.flush(); // write logs/screenshots to report file
        }
    }
    // Getter used by page objects/tests
    public WebDriver getDriver() {
        return driver.get();
    }
    // Optional helper to set ExtentTest for current thread
    public void setTestNode(ExtentTest test) {
        testNode.set(test);
    }
    public ExtentTest getTestNode() {
        return testNode.get();
    }
}