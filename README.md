# Automation Exercise — QA Automation & Load Testing Suite

This repository contains my end-to-end Quality Assurance project for **[Automation Exercise](https://www.automationexercise.com/)**. It covers the full testing lifecycle—from manual test design to automated UI testing and API load testing.

---

## What's Inside

* **Manual Test Suite (35 Test Cases):** Covers positive, negative, smoke, and end-to-end flows (`TC_AE_001` to `TC_AE_035`), including user registration, login validation, product search, category filters, cart updates, checkout, and invoice downloads.
* **UI Automation (`automation-exercise-selenium-e2e`):** Built with **Java 17**, **Selenium WebDriver**, and **TestNG** using the Page Object Model (POM). It handles dynamic user generation, explicit waits, and Google Vignette ad popups automatically.
* **Performance & Load Testing (`automation-exercise-k6-load-test`):** Written in **k6 (JavaScript)** to test how the storefront pages and REST APIs handle concurrent users under smoke, normal load, and sudden traffic spikes.

---

## Prerequisites

Make sure you have the following installed on your system:

* **Java 17+** and **Apache Maven** (for running Selenium tests)
* **Google Chrome**
* **k6** (for running load tests)

Install everything on macOS with one command:

```bash
brew install openjdk@17 maven k6
```

---

## How to Run

### 1. Clone the Repository

```bash
git clone [https://github.com/pujan-bhandari12/AutomationExercise.git](https://github.com/pujan-bhandari12/AutomationExercise.git)
cd AutomationExercise
```

### 2. Run Java + Selenium UI Automation Tests

```bash
cd ../automation-exercise-Selenium
mvn clean test
```

Open the TestNG HTML report on macOS:

```bash
open target/surefire-reports/emailable-report.html
```

> **Note:** Selenium Manager automatically downloads the matching ChromeDriver for your system, so no manual driver setup is needed.

### 3. Run k6 Load & Performance Tests

Move into the k6 folder:

```bash
cd ../automation-exercise-k6-load-test
```

* **Option A — Smoke Test (Quick 15-second health check):**

```bash
k6 run tests/smoke.test.js
```

* **Option B — Full API Load Test (Up to 25 virtual users):**

```bash
k6 run tests/api-load.test.js
```

* **Option C — Web Storefront Browsing Load Test:**

```bash
k6 run tests/web-e2e-load.test.js
```

* **Option D — High-Traffic Spike & Stress Test (Up to 80 virtual users):**

```bash
k6 run tests/stress-spike.test.js
```

Open the generated HTML performance report on macOS:

```bash
open reports/api-load-report.html
```

---

## Author

**Pujan Bhandari**  
QA Engineer | Test Automation & Performance Testing  
Portfolio: [pujanbhandari.com.np](https://pujanbhandari.com.np)

