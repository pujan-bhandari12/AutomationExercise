# Automation Exercise — QA Automation & Load Testing Suite

This repository contains my end-to-end Quality Assurance project for **[Automation Exercise](https://www.automationexercise.com/)**. I built it to cover the full testing lifecycle—from manual test design to automated UI testing and API load testing.

## What's Inside

* **Manual Test Suite (35 Test Cases):** Covers positive, negative, smoke, and end-to-end flows (`TC_AE_001` to `TC_AE_035`), including user registration, login validation, product search, category filters, cart updates, checkout, and invoice downloads.
* **UI Automation (`automation-exercise-selenium-e2e`):** Built with **Java 17**, **Selenium WebDriver**, and **TestNG** using the Page Object Model (POM). It handles dynamic user generation, explicit waits, and Google Vignette ad popups automatically.
* **Performance & Load Testing (`automation-exercise-k6-load-test`):** Written in **k6 (JavaScript)** to test how the storefront pages and REST APIs handle concurrent users under smoke, normal load, and sudden traffic spikes.

---

## Project Structure

```text
├── automation-exercise-selenium-e2e/       # Java + Selenium + TestNG UI tests
│   ├── src/test/java/
│   │   ├── base/BaseTest.java              # Browser setup, waits & ad handling
│   │   ├── pages/                          # Page Object classes (Home, Login, Products, Cart, Checkout)
│   │   └── tests/AutomationExerciseSuiteTest.java
│   ├── pom.xml
│   └── testng.xml
│
└── automation-exercise-k6-load-test/       # k6 load & stress testing scripts
    ├── .github/workflows/k6-load-test.yml  # GitHub Actions CI workflow
    ├── config/thresholds.js                # Response time SLAs & virtual user stages
    ├── tests/
    │   ├── smoke.test.js                   # Quick health check (2 VUs)
    │   ├── api-load.test.js                # Catalog APIs + full user account API lifecycle
    │   ├── web-e2e-load.test.js            # Storefront browsing journey under load
    │   └── stress-spike.test.js            # High-traffic spike test (up to 80 VUs)
    ├── utils/helpers.js
    └── reports/                            # Generated HTML & JSON test reports
