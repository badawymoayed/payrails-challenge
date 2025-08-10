package com.payrails.challenge.core;

import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotenvException;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeSuite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class BaseTest {

    private static final Logger log = LoggerFactory.getLogger(BaseTest.class);
    protected String apiKey;

    @BeforeSuite(alwaysRun = true)
    public void setup() {
        try {
            Dotenv dotenv = Dotenv.load();
            apiKey = dotenv.get("ALPHAVANTAGE_API_KEY");
            log.info("API Key loaded from .env file.");
        } catch (DotenvException e) {
            apiKey = System.getenv("ALPHAVANTAGE_API_KEY");
            log.info("API Key loaded from system environment variable.");
        }

        RestAssured.baseURI = "https://www.alphavantage.co";

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