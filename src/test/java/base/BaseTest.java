package base;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeClass;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import utils.ConfigManager;

public class BaseTest {

    protected static final Logger logger = LogManager.getLogger(BaseTest.class);

    @BeforeClass
    public void setup() {
        logger.info("Initializing API Test Configuration...");
        
        RestAssured.baseURI = ConfigManager.getProperty("base.url", "https://petstore.swagger.io/v2");
        logger.info("Base URI set to: " + RestAssured.baseURI);
        
        // Chain the filters for complete Console and Allure visibility
        RestAssured.filters(
                new RequestLoggingFilter(),  // Prints full request to console
                new ResponseLoggingFilter(), // Prints full response to console
                new AllureRestAssured()      // Attaches everything to Allure
        );
    }
}