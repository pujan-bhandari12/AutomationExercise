# Automation Exercise — k6 Performance & Load Testing Suite

A modular **k6** (JavaScript) load, stress, and spike testing framework for [Automation Exercise](https://www.automationexercise.com/), covering both **Frontend Web Pages** and **REST API Endpoints** (`/api/productsList`, `/api/brandsList`, `/api/searchProduct`, `/api/createAccount`, `/api/verifyLogin`, `/api/getUserDetailByEmail`, `/api/deleteAccount`).

---

## 📁 Project Structure

```text
automation-exercise-k6-load-test/
├── .github/
│   └── workflows/
│       └── k6-load-test.yml      # GitHub Actions CI workflow
├── config/
│   └── thresholds.js             # Shared SLA thresholds & stage profiles
├── tests/
│   ├── smoke.test.js             # Quick sanity check (2 VUs)
│   ├── api-load.test.js          # REST API load test + E2E User API lifecycle
│   ├── web-e2e-load.test.js      # Storefront web browsing journey under load
│   └── stress-spike.test.js      # High-concurrency stress & traffic spike test
├── utils/
│   └── helpers.js                # Dynamic test user generator & JSON validators
├── reports/
│   └── .gitkeep                  # Standalone HTML & JSON report output directory
├── .gitignore
├── package.json                  # Optional npm script shortcuts
└── README.md
```

---

## 🛠️ Prerequisites (macOS)

Install **k6** via Homebrew:
```bash
brew install k6
```

Verify installation:
```bash
k6 version
```

---

## 🚀 How to Run Tests

```bash
# 1. Smoke Test
k6 run tests/smoke.test.js

# 2. API Load Test
k6 run tests/api-load.test.js

# 3. Web Storefront Browsing Load Test
k6 run tests/web-e2e-load.test.js

# 4. Stress & Spike Test
k6 run tests/stress-spike.test.js
```

### Override Base URL or VUs via CLI
```bash
k6 run -e BASE_URL=https://www.automationexercise.com --vus 20 --duration 1m tests/api-load.test.js
```

---

## 📊 Reports

Each test automatically exports a standalone HTML dashboard (`reports/*.html`) and JSON summary (`reports/*.json`) upon completion.
