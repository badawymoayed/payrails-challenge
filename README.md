# My Submission for the Payrails Technical Challenge

### 👋 Hi there!
Thanks for the opportunity to tackle this challenge. I was excited to get started because it touched on so many areas I'm passionate about: building solid automation, thinking about test architecture, and learning about new APIs. I've built this framework to showcase not just my code, but my entire approach to quality.

### 🛠️ The Tech I Used
I decided to build this framework using a stack I'm really comfortable with:
- **Language**: Java 11+
- **Project Tool**: Apache Maven
- **API Testing**: REST Assured
- **Test Runner**: TestNG
- **Logging**: SLF4J with Logback

### 🧠 The Journey to This Framework: Research & Strategic Decisions
My approach was to treat this task as a real-world project. Before writing any code, I conducted a deep dive into Payrails' engineering culture and tech stack, based on the provided job description and company resources. Here's a summary of my findings and how they influenced my final decisions:

* **Engineering Culture**: Payrails values "operational excellence," "open communication," and engineers who "share knowledge and learn continuously." The framework is built to be modular, well-documented, and easy to run, directly reflecting these principles.

* **Backend Technology**: My research confirmed that Payrails' backend is written in **Go**. While I have some understanding of Go, I chose to build this framework in **Java** because it's where my professional expertise lies. This was a pragmatic decision to ensure the highest quality of code and a robust architecture. I believe it's better to deliver an excellent project in a language I know than a rushed project in a new one.

* **CI/CD**: The job description explicitly mentions **GitHub Actions** as part of Payrails' tech stack. To align perfectly with this, I designed and implemented a GitHub Actions workflow, ensuring the framework can be integrated as a seamless quality gate into a modern CI/CD pipeline.

* **API vs. UI Testing**: The job description also mentioned `Playwright (Typescript)`. My decision to use **REST Assured** instead of a UI-focused tool demonstrates a clear understanding of the difference between API and UI testing, and a professional ability to select the right tool for a specific task.

### 🧱 How I Built It (My Framework's Architecture)
I designed this framework with a layered, modular approach. Think of it like building with LEGOs: each piece has a single job, and you can swap them out or add new ones without the whole thing falling apart.

-   **Core & Utilities**: This is the foundation. It handles all the behind-the-scenes work, like securely getting the API key and setting up the base URL.
-   **API Clients**: This is where the magic happens. I built a separate class for each API endpoint. This keeps the technical details of the API call separate from the actual test logic, which makes the code super clean and easy to maintain.
-   **Test Suites**: This is where my tests live. They're data-driven, so I can add new test cases just by adding a new line of data to a table. It's a great way to scale.

### ✅ My Test Plan in Action
I designed a series of test cases that cover a variety of scenarios. For this challenge, I chose to automate a few key ones to show off my technical skills.

| Test Case ID | Category | Description | Automated? |
|--------------|----------------|-------------------------------------------------------------------------------------------------------------------------------------------------|-----------------------------------------------------|
| TC-01        | Symbol Search  | A successful search for a known keyword returns a 200 OK and a list of relevant matches. | ✅ Yes (with schema, performance, and data validation) |
| TC-02        | Symbol Search  | A search for a keyword with no matches returns an empty `bestMatches` list. | ❌ No |
| TC-03        | Symbol Search  | An API request without a mandatory parameter returns an error. | ❌ No |
| TC-04        | Time Series | A successful request for stock data (Daily, Weekly, Monthly) returns a 200 OK and the correct metadata. | ✅ Yes (with data-driven validation for all three) |
| TC-05        | Time Series | An API request for a non-existent stock symbol returns an error. | ❌ No |
| TC-06        | Authentication | An API request with an invalid API key returns an authentication error. | ❌ No |
| TC-07        | Authentication | An API request with a missing API key returns an error. | ❌ No |

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

Thanks again for the chance to work on this! I'm looking forward to discussing it further.