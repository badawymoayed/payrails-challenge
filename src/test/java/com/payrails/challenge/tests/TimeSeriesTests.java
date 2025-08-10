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

    /**
     * Provides test data for various time series functions and symbols.
     * @return A 2D object array with function name, symbol, and expected symbol.
     */
    @DataProvider(name = "timeSeriesData")
    public Object[][] timeSeriesData() {
        return new Object[][] {
                {"TIME_SERIES_DAILY", "IBM", "IBM"},
                {"TIME_SERIES_WEEKLY", "MSFT", "MSFT"},
                {"TIME_SERIES_MONTHLY", "TSLA", "TSLA"}
        };
    }

    /**
     * TC-04: Verifies successful data retrieval for various time series.
     * This data-driven test validates status code, schema, response time, and metadata.
     */
    @Test(dataProvider = "timeSeriesData", description = "TC-04: Verifies successful data retrieval")
    public void testSuccessfulTimeSeries(String function, String symbol, String expectedSymbol) {
        log.info("Starting test with function: {}, symbol: {}, and expected symbol: {}", function, symbol, expectedSymbol);

        TimeSeriesApiClient client = new TimeSeriesApiClient();
        Response response = client.getTimeSeries(function, symbol, apiKey);

        log.debug("API Response Body for function '{}' and symbol '{}':\n{}", function, symbol, response.asPrettyString());

        response.then()
                // Assert 1: The status code is 200 OK
                .statusCode(200)

                // Assert 2: The response body structure matches our JSON schema
                // NOTE: This schema is for DAILY. Weekly/Monthly would need their own schemas.
                .body(matchesJsonSchemaInClasspath("jsonschema/timeseries-daily-schema.json"))

                // Assert 3: The response time is within an acceptable threshold
                .time(lessThan(5000L))

                // Assert 4: The response body contains the correct symbol in the metadata
                .body("'Meta Data'.'2. Symbol'", equalTo(expectedSymbol));

        log.info("Test for function '{}' and symbol '{}' passed all validations.", function, symbol);
        pause(2);
    }
}