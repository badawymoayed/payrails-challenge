package com.payrails.challenge.core;

import io.github.cdimascio.dotenv.Dotenv;
import io.restassured.RestAssured;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class BaseTest {

    private static final Logger log = LoggerFactory.getLogger(BaseTest.class);
    protected String apiKey;

    @BeforeSuite(alwaysRun = true)
    public void setup() {
        Dotenv dotenv = Dotenv.load();
        RestAssured.baseURI = "https://www.alphavantage.co";
        apiKey = dotenv.get("ALPHAVANTAGE_API_KEY");

        if (apiKey == null || apiKey.trim().isEmpty()) {
            throw new IllegalStateException("The environment variable 'ALPHAVANTAGE_API_KEY' is not set. Please configure it to run the tests.");
        }
        log.info("API Key loaded and environment setup successful.");
    }

    // We add a pause at the end of the entire test suite to respect the API rate limit
    @AfterSuite(alwaysRun = true)
    public void teardown() {
        log.info("Pausing for 15 seconds to respect AlphaVantage API rate limit.");
        pause(15);
    }

    protected void pause(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}