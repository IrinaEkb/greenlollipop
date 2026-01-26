# Selenium + TestNG Automation Framework

This framework contains both UI and API tests with reusable base classes, configuration management, retry logic, listeners, screenshots, and Excel helpers.

## Project Structure
src/
├── main/
│ ├── java/
│ │ ├── Base/ (BaseTest, BasePage)
│ │ ├── driver/ (WebDriver manager - ThreadLocal)
│ │ ├── utils/ (helpers: retry, listeners, screenshots, config, Excel)
│ │ ├── pages/ (page objects)
│ │ └── helpers/ (additional helpers)
│ └── resources/
├── test/
│ ├── java/
│ │ ├── ui/ (UI tests - TestNG)
│ │ └── api/ (API tests - TestNG)
│ └── resources/
│ ├── testdata/
│ └── testng/
├── pom.xml
└── env.properties

## Configuration
env.properties:
env=qa
qa.url=https://practice.expandtesting.com
stage.url=https://practice.expandtesting.com
api.baseurl=https://practice.expandtesting.com/notes/api

## How to run
Run Smoke suite (UI + API):
mvn clean test -Psmoke

Run Regression suite (UI + API):
mvn clean test -Pregression

Run only UI tests (by groups):
mvn clean test -Dgroups=ui

Run only API tests (by groups):
mvn clean test -Dgroups=api
