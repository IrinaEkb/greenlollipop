package Base;

import driver.DriverManager;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;
import utils.EnvConfig;

public abstract class BaseTest {

    protected WebDriver driver;

    @BeforeClass(alwaysRun = true)
    @Parameters("env")
    public void setUp(@Optional("qa") String env) {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        DriverManager.setDriver(driver);
        driver.get(EnvConfig.getBaseUrl());
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        DriverManager.quitDriver();
    }

    public WebDriver getDriver() {
        return driver;
    }
}