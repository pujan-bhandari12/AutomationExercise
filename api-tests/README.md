# Automation Exercise — API Test Automation Suite

Automated REST API testing suite built with **Postman** and executed via **Newman** CLI & **GitHub Actions**, targeting [Automation Exercise](https://www.automationexercise.com/).

## Test Coverage
* **Products & Catalog:** Fetching product lists, product search validation, and negative HTTP method validation (`405 Method Not Allowed`).
* **User Account Lifecycle (E2E Chaining):** Dynamic test user generation via Pre-request scripts (`POST /createAccount`), authentication validation (`POST /verifyLogin`), and automated test data cleanup (`DELETE /deleteAccount`).

## How to Run Locally
1. Install dependencies: `npm install`
2. Run tests & generate HTML report: `npm test`
