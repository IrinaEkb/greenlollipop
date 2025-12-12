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
import java.util.Scanner;
public class BaseTest {
    protected static ThreadLocal<WebDriver> driver = new ThreadLocal<>(); // separate driver per thread
    protected static ThreadLocal<ExtentTest> testNode = new ThreadLocal<>(); // separate report node per thread
    protected static ExtentReports extent; // global report instance

    @BeforeSuite(alwaysRun = true)
    @Parameters({"env"})
    public void setupReport(@Optional String env) {
        // prepare HTML report file
        ExtentSparkReporter reporter = new ExtentSparkReporter(
                "reports/ExtentReport_" + System.currentTimeMillis() + "_" + env + ".html"
        );
        extent = new ExtentReports();
        extent.attachReporter(reporter); // attach reporter to global report
    }

    // check if test belongs to Smoke group
    private boolean isSmokeTest(ITestResult result) {
        for (String group : result.getMethod().getGroups()) {
            if ("smoke".equalsIgnoreCase(group)) return true;
        }
        return false;
    }

    @BeforeMethod(alwaysRun = true)
    @Parameters({"env"})
    public void setUp(ITestResult result, @Optional String env) {
        // always start fresh browser per test for full isolation
        createDriver(env);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        String[] groups = result.getMethod().getGroups();

        boolean isSmoke = isSmokeTest(result);
        boolean isRegression = false;
        for (String group : groups) {
            if ("regression".equalsIgnoreCase(group)) isRegression = true;
        }

        // screenshots: smoke always, regression only on failure
        if (isSmoke) ScreenshotUtils.captureScreenshot(getDriver(), testName + "_SMOKE");
        if (isRegression && result.getStatus() == ITestResult.FAILURE)
            ScreenshotUtils.captureScreenshot(getDriver(), testName + "_REGRESSION_FAIL");

        // pause in debug mode to inspect failing test
        if (System.getProperty("debugMode", "false").equals("true")) {
            System.out.println("Debug mode: press Enter to close browser...");
            try (Scanner scanner = new Scanner(System.in)) { scanner.nextLine(); }
            catch (Exception ignored) {}
        }

        // close browser to free resources
        if (driver.get() != null) {
            driver.get().quit(); // terminate all windows/processes
            driver.remove();     // clear ThreadLocal
        }

        // clear report node to avoid memory leaks
        if (testNode.get() != null) testNode.remove();
    }

    @AfterSuite(alwaysRun = true)
    public void flushReport() {
        if (extent != null) extent.flush(); // save report to file
    }

    // create and configure WebDriver
    private void createDriver(String env) {
        WebDriverManager.chromedriver().setup(); // ensure chromedriver is available
        driver.set(new ChromeDriver());          // assign new driver to current thread
        driver.get().manage().window().maximize(); // maximize for visual clarity
        driver.get().manage().timeouts().implicitlyWait(Duration.ofSeconds(3)); // small implicit wait for static elements
        driver.get().get(EnvConfig.getUrl(env)); // navigate to environment-specific URL
    }

    public WebDriver getDriver() { return driver.get(); } // access driver from subclasses

    public void setTestNode(ExtentTest test) { testNode.set(test); } // assign report node

    public ExtentTest getTestNode() { return testNode.get(); } // access report node
}