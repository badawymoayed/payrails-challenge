package com.payrails.challenge.core;

import io.github.cdimascio.dotenv.Dotenv;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class BaseTest {

    private static final Logger log = LoggerFactory.getLogger(BaseTest.class);
    protected String apiKey;

    @BeforeClass(alwaysRun = true)
    public void setup() {
        Dotenv dotenv = Dotenv.load();
        RestAssured.baseURI = "https://www.alphavantage.co";
        apiKey = dotenv.get("ALPHAVANTAGE_API_KEY");

        // Log the API key to ensure it's loaded correctly
        log.info("API Key loaded successfully: {}", apiKey);

        if (apiKey == null || apiKey.trim().isEmpty()) {
            throw new IllegalStateException("The environment variable 'ALPHAVANTAGE_API_KEY' is not set. Please configure it to run the tests.");
        }
    }

    protected void pause(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}