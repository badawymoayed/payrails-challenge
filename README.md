# Payrails API Test Automation Challenge

### 👋 Introduction

Thank you for the opportunity to take on this technical challenge. This project is a comprehensive API test automation framework built from the ground up to test the AlphaVantage API. My goal was to create a solution that demonstrates not just functional test code, but also a deep understanding of modern QA principles, including robust framework architecture, scalable design, and seamless CI/CD integration.

### 🛠️ Technology Stack

This framework was built using a robust, industry-standard Java ecosystem to ensure quality and maintainability.

* **Language**: Java 11+
* **Build Tool**: Apache Maven
* **API Testing Library**: REST Assured
* **Test Runner**: TestNG
* **Reporting**: Allure & ExtentReports
* **Logging**: SLF4J with Logback
* **CI/CD**: GitHub Actions

### 🧠 Strategic Decisions & Architectural Philosophy

Before writing a single line of code, I conducted a deep dive into Payrails' engineering culture and technology stack. This research was the foundation for all my technical decisions.

* **Choice of Java**: My research confirmed that Payrails' backend is written in **Go**. I made the pragmatic decision to build this framework in **Java** because it's where my professional expertise lies. This ensures the highest quality of code and a truly robust architecture that meets the core requirements of the challenge. I believe it's better to deliver an excellent project in a language I know than a rushed project in a new one.

* **API vs. UI Tooling**: Although the Payrails stack includes `Playwright (Typescript)`, I chose **REST Assured** for this challenge. This demonstrates a clear understanding of selecting the best, most specialized tool for a given task—in this case, a dedicated library for API contract and functional validation.

* **Architectural Doctrine**: This framework is a direct reflection of the Payrails doctrine of **Modularity, Agnosticism, and Scale**.
    * **Modular:** The 3-layer architecture (API Clients, Tests, Core) ensures that each component is independent and can be maintained or extended without impacting others.
    * **Agnostic:** The framework is environment-agnostic, using `.env` files and GitHub Secrets to run anywhere without code changes.
    * **Scalable:** The design is built for growth, allowing for the easy addition of new tests and API clients.

### 🏗️ Framework Architecture

The project follows a clean, 3-layer design pattern to ensure a clear separation of concerns.

* **Layer 1: API Clients (`api` package)**: This layer contains classes responsible for all the technical details of making API calls with REST Assured. They act as a bridge between the tests and the API, hiding the implementation complexity.
* **Layer 2: Tests (`tests` package)**: This layer contains the TestNG test classes. The tests are clean, readable, and focused on *what* to test, not *how*. They use the API clients to perform actions and assert on the responses.
* **Layer 3: Core (`core` package)**: This is the foundational layer, containing the `BaseTest` class for common setup and teardown logic, as well as reporting listeners.

### ✅ Test Plan & Coverage

This framework is designed to validate the API's behavior under various conditions. The following test cases were designed, with a strategic selection automated for this POC.

| Test Case ID | Test Category | Description and Gherkin Steps | Automated? |
| :--- | :--- | :--- | :--- |
| **TC-01** | Symbol Search | **Positive: Successful search for a known symbol.**<br>**Given** I have a valid API key.<br>**When** I search for "IBM".<br>**Then** the API returns a 200 OK, matches the schema, and includes "IBM" in the results. | ✅ Yes |
| **TC-02** | Symbol Search | **Positive: Search for a keyword with no matches.**<br>**Given** I have a valid API key.<br>**When** I search for a non-matching keyword.<br>**Then** the API returns a 200 OK and an empty `bestMatches` list. | ✅ Yes |
| **TC-03** | Symbol Search | **Negative: Missing mandatory parameter.**<br>**Given** I have a valid API key.<br>**When** I search without a `keywords` parameter.<br>**Then** the API returns an error message. | ❌ No |
| **TC-04** | Time Series | **Positive: Successful data retrieval.**<br>**Given** I have a valid API key.<br>**When** I request time series data for "MSFT" (DAILY, WEEKLY, MONTHLY).<br>**Then** the API returns a 200 OK and matches the correct schema. | ✅ Yes |
| **TC-05** | Time Series | **Negative: Non-existent symbol.**<br>**Given** I have a valid API key.<br>**When** I request data for a non-existent symbol.<br>**Then** the response body contains an error message. | ❌ No |
| **TC-06** | Authentication | **Negative: Invalid API key.**<br>**Given** I use an invalid API key.<br>**When** I make an API request.<br>**Then** the API returns an authentication error. | ❌ No |
| **TC-07** | Authentication | **Negative: Missing API key.**<br>**Given** I make an API request.<br>**When** I do not provide the `apikey` parameter.<br>**Then** the API returns an error response. | ❌ No |

### 🚀 Getting Started

I designed this project to be easy to run both locally and on CI.

**1. Clone the Repository:**
`git clone https://github.com/badawymoayed/payrails-challenge.git`

**2. Get an API Key:**
Claim a free API key from [AlphaVantage](https://www.alphavantage.co/support/#api-key).

**3. Configure Your Local Environment:**
In the project's root directory, create a file named `.env` and add your key:
`ALPHAVANTAGE_API_KEY=YOUR_KEY_HERE`

**4. Run Tests Locally:**
Open a terminal in the project's root and run:
`mvn clean test`

**5. View Reports Locally:**
* **ExtentReport:** Open `target/extent-report.html` in your browser.
* **Allure Report:** Run `mvn io.qameta.allure:allure-maven:serve`.

###  Scaling the Framework

This framework was designed with scalability in mind, aligning with Payrails' microservices architecture. Here are two key strategies for scaling its use across an organization:

#### 1. Targeted Test Suites (Smoke vs. Regression)
The framework uses TestNG `groups` to tag tests as either `smoke` or `regression`. This allows the CI/CD pipeline to run different test suites for different purposes:
- A fast **smoke suite** (`run_smoke_tests` job) can be run on every code push for quick feedback.
- A full **regression suite** (`build_and_test_regression` job) can be run nightly or before a release for comprehensive coverage.

#### 2. Reusable CI/CD Workflows
To avoid duplicating test logic across multiple microservice repositories, this framework's CI/CD pipeline can be converted into a **reusable workflow**. A central QA repository would host this framework, and individual microservice pipelines would simply "call" it.

**Example Caller Workflow (in another microservice repo):**
```yaml
name: Run Integration Tests
on: [push]
jobs:
  run_api_tests:
    uses: badawymoayed/payrails-challenge/.github/workflows/ci.yml@main
    secrets:
      API_KEY: ${{ secrets.SERVICE_API_KEY }}