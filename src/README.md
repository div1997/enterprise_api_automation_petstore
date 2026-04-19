Prerequisites & Initial Setup
Before running the framework, ensure you have the following installed on your local machine:

Java Development Kit (JDK): Version 8 or 11+ installed and JAVA_HOME configured.

Maven: Installed and added to your system's PATH.

IDE: IntelliJ IDEA, Eclipse, or VS Code.

Note :for IDE users: Ensure the Lombok Plugin is installed and "Enable Annotation Processing" is checked in your IDE settings.
Folder Structure:
src/
├── main/
│   ├── java/
│   │   ├── api/                  # Reusable API request methods (GET, POST, PUT, DELETE)
│   │   │   └── PetApi.java
│   │   ├── config/               # Centralized configuration and endpoint management
│   │   │   ├── Routes.java
│   │   │   └── ConfigManager.java
│   │   ├── pojo/                 # Payload structures using Encapsulation/Lombok
│   │   │   ├── Category.java
│   │   │   ├── Pet.java
│   │   │   └── Tag.java
│   │   └── utils/                # Utility helpers (e.g., Faker for random data)
│   │       └── TestUtils.java
│   └── resources/
│       └── config.properties     # Base URLs and environment variables
├── test/
│   ├── java/
│   │   ├── base/                 # Base Test for setup/teardown
│   │   │   └── BaseTest.java
│   │   ├── listeners/            # TestNG Listeners for logging/reporting
│   │   │   └── TestListener.java
│   │   └── tests/                # Actual TestNG test classes
│   │       └── PetTests.java
│   └── resources/
│       └── testng.xml            # Suite execution file

=================================================
How to Run the Tests:
You can execute the entire test suite directly from your terminal using Maven. This is the exact command used by CI/CD pipelines.
Clone the repository and navigate to the project root:
Bash:
git clone <repository-url>
cd <project-folder>
Execute the TestNG Suite:
=================================================
Bash:
mvn clean test
This command compiles the project, resolves dependencies, and runs the tests defined in src/test/resources/testng.xml.
===================================================
Viewing the Allure Execution Report:
Allure provides a rich, executive-level dashboard showing test behaviors, timelines, and raw HTTP traffic.
After a test run completes, generate and serve the report by running:
Bash:
mvn allure:serve
This will process the raw data in the target/allure-results folder and automatically open the interactive HTML report in your default web browser.
======================================================
Guide: How to Add a New API Test
To extend the framework and add tests for a new endpoint, follow this standard workflow:

Add the Endpoint: Define the new API path in src/main/java/config/Routes.java.

Create POJOs: If the request requires a JSON body, create a new Java class in the pojo package using @Data and @Builder annotations to represent the JSON structure.

Create API Actions: In the api package, add a new method using REST Assured to handle the HTTP call (e.g., public static Response createItem(Item payload)). Annotate this method with Allure's @Step.

Write the Test: In the tests package, write your TestNG @Test method. Use the Builder pattern to create your payload, call the API action method, and assert the Response status codes and body.
============================================================

Logging (Console & File):
This framework uses Log4j2 for comprehensive logging.
Real-time Console Logs: During execution, full Request URIs, Headers, Payloads, and Responses are printed directly to the terminal for immediate debugging.
Rolling File Logs: Logs are also permanently saved to the logs/api-automation.log file, which rolls over based on file size and date.
================================================================
Next Implementation:
CI/CD Integration Readinessgit
This framework is natively structured to integrate seamlessly with modern CI/CD tools like GitHub Actions.

To implement a pipeline:

Create a .github/workflows/api-tests.yml file.

Set up a cron schedule trigger or a push trigger.

Use an Ubuntu/macOS runner to check out the code, setup Java, and execute mvn clean test.

Optionally, route the generated target/allure-results to an Allure reporting action to publish test results automatically to GitHub Pages.

