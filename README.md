GreenLollipop — Selenium + TestNG Automation Framework

Selenium + TestNG automation framework for UI and API testing.
Includes reusable base classes, configuration management, listeners, retry logic, screenshots, and Excel utilities.

Project structure
src/
├── main/
│   ├── java/
│   │   ├── Base/         (BaseTest, BasePage)
│   │   ├── driver/       (WebDriver manager - ThreadLocal)
│   │   ├── utils/        (helpers: retry, listeners, screenshots, config, Excel)
│   │   ├── pages/        (page objects)
│   │   └── helpers/      (additional helpers)
│   └── resources/
├── test/
│   ├── java/
│   │   ├── ui/           (UI tests - TestNG)
│   │   └── api/          (API tests - TestNG)
│   └── resources/
│       ├── testdata/
│       └── testng/
pom.xml
env.properties


Technologies
Maven
Java 21
Selenium WebDriver
TestNG
Allure Report
RestAssured
Apache POI (Excel)
WebDriverManager
Log4j2


Configuration
env.properties contains environments and URLs.


How to run
mvn clean test