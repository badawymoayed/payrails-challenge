package com.payrails.challenge.tests;

import com.payrails.challenge.api.TimeSeriesApiClient;
import com.payrails.challenge.core.BaseTest;
import io.restassured.response.Response;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;

/**
 * Test suite for the Time Series API endpoints.
 */
public class TimeSeriesTests extends BaseTest {

    private static final Logger log = LoggerFactory.getLogger(TimeSeriesTests.class);

    @DataProvider(name = "timeSeriesData")
    public Object[][] timeSeriesData() {
        return new Object[][] {
                {"TIME_SERIES_DAILY", "IBM", "IBM", "jsonschema/timeseries-daily-schema.json"},
                {"TIME_SERIES_WEEKLY", "MSFT", "MSFT", "jsonschema/timeseries-weekly-schema.json"},
                {"TIME_SERIES_MONTHLY", "TSLA", "TSLA", "jsonschema/timeseries-monthly-schema.json"}
        };
    }

    @Test(dataProvider = "timeSeriesData", description = "TC-04: Verifies successful data retrieval")
    public void testSuccessfulTimeSeries(String function, String symbol, String expectedSymbol, String schemaPath) {
        log.info("Starting test with function: {}, symbol: {}, and expected symbol: {}", function, symbol, expectedSymbol);

        TimeSeriesApiClient client = new TimeSeriesApiClient();
        Response response = client.getTimeSeries(function, symbol, apiKey);

        log.debug("API Response Body for function '{}' and symbol '{}':\n{}", function, symbol, response.asPrettyString());

        response.then()
                .statusCode(200)
                .time(lessThan(5000L))
                .body("'Meta Data'.'2. Symbol'", equalTo(expectedSymbol))
                // DYNAMIC SCHEMA VALIDATION: Uses the schema path from the DataProvider
                .body(matchesJsonSchemaInClasspath(schemaPath));

        log.info("Test for function '{}' and symbol '{}' passed all validations.", function, symbol);
        pause(15); // Adding a 15-second pause after each call to respect the API limit
    }
}