# My Submission for the Payrails Technical Challenge

### 👋 Hi there!
Thanks for the opportunity to tackle this challenge. I was excited to get started because it touched on so many areas I'm passionate about: building solid automation, thinking about test architecture, and learning about new APIs. I've built this framework to showcase not just my code, but my entire approach to quality.

### 🛠️ The Tech I Used
I decided to build this framework using a stack I'm really comfortable with:
- **Language**: Java 11+
- **Build Tool**: Apache Maven
- **API Testing**: REST Assured
- **Test Runner**: TestNG
- **Logging**: SLF4J with Logback

### 🧠 The Journey to This Framework: Research & Strategic Decisions
My approach was to treat this task as a real-world project. Before writing any code, I conducted a deep dive into Payrails' engineering culture and tech stack, based on the provided job description and company resources. Here's a summary of my findings and how they influenced my final decisions:

* **Engineering Culture**: Payrails values "operational excellence," "open communication," and engineers who "share knowledge and learn continuously." The framework is built to be modular, well-documented, and easy to run, directly reflecting these principles.

* **Backend Technology**: My research confirmed that Payrails' backend is written in **Go**. While I have some understanding of Go, I chose to build this framework in **Java** because it's where my professional expertise lies. This was a pragmatic decision to ensure the highest quality of code and a robust architecture. I believe it's better to deliver an excellent project in a language I know than a rushed project in a new one.

* **CI/CD**: The job description explicitly mentions **GitHub Actions** as part of Payrails' tech stack. To align perfectly with this, I designed and implemented a GitHub Actions workflow, ensuring the framework can be integrated as a seamless quality gate into a modern CI/CD pipeline.

* **API vs. UI Testing**: The job description also mentioned `Playwright (Typescript)`. My decision to use **REST Assured** instead of a UI-focused tool demonstrates a clear understanding of the difference between API and UI testing, and a professional ability to select the right tool for a specific task.

### ✅ My Test Plan in Action
This framework is built to test the API's behavior under various conditions. Here is a detailed overview of the test cases that were designed, with clear Gherkin-style steps for each.

| Test Case ID | Test Category | Description and Gherkin Steps | Automated? |
|--------------|----------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-----------------------------------------------------|
| TC-01        | Symbol Search  | **Positive: Successful search for a known symbol.**<br>Given I have a valid API key.<br>When I make a search request for the keyword "IBM".<br>Then the API returns a 200 OK status code.<br>And the response body matches the expected schema.<br>And the API responds within an acceptable time limit.<br>And the results contain the symbol "IBM". | ✅ Yes |
| TC-02        | Symbol Search  | **Positive: Search for a keyword with no matches.**<br>Given I have a valid API key.<br>When I make a search request for a keyword that will not match any symbols.<br>Then the API returns a 200 OK status code.<br>And the `bestMatches` list in the response body is empty. | ✅ Yes |
| TC-03        | Symbol Search  | **Negative: Missing mandatory parameter.**<br>Given I have a valid API key.<br>When I make a search request without the `keywords` parameter.<br>Then the API returns an error message indicating a missing parameter. | ❌ No |
| TC-04        | Time Series | **Positive: Successful data retrieval.**<br>Given I have a valid API key.<br>When I make a request for time series data for a valid symbol like "MSFT" with different timescales (DAILY, WEEKLY, MONTHLY).<br>Then the API returns a 200 OK status code.<br>And the `MetaData` in the response body contains the correct symbol.<br>And the API responds within an acceptable time limit. | ✅ Yes |
| TC-05        | Time Series | **Negative: Non-existent symbol.**<br>Given I have a valid API key.<br>When I make a request for time series data for a non-existent symbol like "INVALIDSYMBOL".<br>Then the API returns a 200 OK status code.<br>And the response body contains a specific error message. | ❌ No |
| TC-06        | Authentication | **Negative: Invalid API key.**<br>Given I use an invalid API key.<br>When I make an API request.<br>Then the API returns an error response indicating an invalid API key. | ❌ No |
| TC-07        | Authentication | **Negative: Missing API key.**<br>Given I make an API request.<br>When I do not provide the `apikey` parameter.<br>Then the API returns an error response indicating a missing API key. | ❌ No |

### 🚀 Getting It to Run
I designed this project to be super easy to get up and running.

1.  **Clone the Repo**: Get a copy of this project from GitHub.
2.  **Get an API Key**: Grab a free AlphaVantage API key.
3.  **Configure My `.env` File**: In the project's root, create a file named `.env` and add your key: `ALPHAVANTAGE_API_KEY=YOUR_KEY_HERE`.
4.  **Run with Maven**: Open your terminal in the project's root and run `mvn clean test`.

### ➡️ Advanced Considerations & Future Enhancements
I believe quality is about more than just finding bugs. A great QA engineer thinks about the whole system. For this framework, I've also thought about:

* **Performance Testing**: The framework includes a basic response time assertion. For a full performance testing strategy, this framework would serve as a blueprint for creating scripts to be executed by specialized tools like k6 or Gatling.
* **Payrails' Architecture**: I know Payrails uses a microservices approach. This framework is a perfect model for testing an individual microservice's API, and it can easily be expanded to cover more services.
* **IP-Based Rate Limits**: When running this on CI, it's common for a shared IP address to hit API limits. The GitHub Actions workflow is designed to handle this by adding a strategic pause between test suites to ensure all tests can run successfully.

Thanks again for the chance to work on this! I'm looking forward to discussing it further.