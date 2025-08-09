package com.payrails.challenge.tests;

import com.payrails.challenge.clients.TimeSeriesApiClient;
import com.payrails.challenge.core.BaseTest;
import io.restassured.response.Response;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.hamcrest.Matchers.lessThan;
import static org.testng.Assert.assertEquals;
import static org.hamcrest.Matchers.equalTo;

public class TimeSeriesTests extends BaseTest {

    private static final Logger log = LoggerFactory.getLogger(TimeSeriesTests.class);

    @DataProvider(name = "timeSeriesData")
    public Object[][] timeSeriesData() {
        return new Object[][] {
                {"TIME_SERIES_DAILY", "IBM", "IBM"},
                {"TIME_SERIES_WEEKLY", "MSFT", "MSFT"},
                {"TIME_SERIES_MONTHLY", "TSLA", "TSLA"}
        };
    }

    @Test(dataProvider = "timeSeriesData")
    public void testSuccessfulTimeSeries(String function, String symbol, String expectedSymbol) {
        log.info("Starting test with function: {}, symbol: {}, and expected symbol: {}", function, symbol, expectedSymbol);

        // Pause for a few seconds to avoid the rate limit
        pause(5);

        TimeSeriesApiClient client = new TimeSeriesApiClient();
        Response response = client.getTimeSeries(function, symbol, apiKey);

        log.debug("API Response Body:\n{}", response.asPrettyString());

        // Assert 1: The status code is 200 OK
        assertEquals(response.getStatusCode(), 200, "The API status code should be 200.");

        // Assert 2: The response body contains the correct symbol in the metadata
        response.then().body("MetaData.\"2. Symbol\"", equalTo(expectedSymbol));

        // Assert 3: The response time is within an acceptable threshold
        response.then().time(lessThan(2000L));

        log.info("Test for function '{}' and symbol '{}' passed all validations.", function, symbol);
    }
}