# GreenLollipop — Selenium + TestNG Automation Framework

## About the project
This is a hybrid automation framework for UI and API testing.
The framework contains a limited set of classes to demonstrate a clean and scalable structure, not a full production project.

## What is included
- UI tests (Selenium + TestNG)
- API tests (RestAssured + TestNG)
- Base classes for tests and pages
- WebDriver manager with ThreadLocal support
- Listeners for logging, screenshots, and retry logic
- Configuration via env.properties
- Excel utilities for test data

## Technologies
- Maven — build and dependency management
- Java 21 — programming language
- Selenium WebDriver — UI automation
- TestNG — test runner and test organization
- Allure Report — test reporting and visualization
- RestAssured — API automation
- Apache POI (Excel) — reading/writing Excel test data
- WebDriverManager — automatic browser driver management
- Log4j2 — logging

## How to run
```bash
# Run Smoke suite (UI + API)
mvn clean test -Psmoke

# Run Regression suite (UI + API)
mvn clean test -Pregression

# Run only UI tests (by groups)
mvn clean test -Dgroups=ui

# Run only API tests (by groups)
mvn clean test -Dgroups=api