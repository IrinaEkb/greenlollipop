package Base;

import driver.DriverManager;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;
import utils.EnvConfig;

import org.apache.logging.log4j.Logger;

public abstract class BaseTest {

    protected WebDriver driver;
    protected static final Logger log = LogManager.getLogger(BaseTest.class);

    @BeforeClass(alwaysRun = true)
    @Parameters("env")
    public void setUp(@Optional("qa") String env) {
        log.info("=== START TESTS ===");
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        DriverManager.setDriver(driver);
        log.info("Opening base URL: {}", EnvConfig.getBaseUrl());
        driver.get(EnvConfig.getBaseUrl());
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        log.info("Closing driver");
        DriverManager.quitDriver();
        log.info("=== TESTS FINISHED ===");
    }

    public WebDriver getDriver() {
        return driver;
    }
}