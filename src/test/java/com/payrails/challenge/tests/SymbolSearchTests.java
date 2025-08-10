package com.payrails.challenge.tests;

import com.payrails.challenge.api.SymbolSearchApiClient;
import com.payrails.challenge.core.BaseTest;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;

/**
 * Test suite for the Symbol Search API endpoint.
 * This class contains all tests related to symbol searching functionality.
 */
public class SymbolSearchTests extends BaseTest {

    private static final Logger log = LoggerFactory.getLogger(SymbolSearchTests.class);

    /**
     * Provides test data for the successful symbol search test.
     * @return A 2D object array with search keywords and their expected symbols.
     */
    @DataProvider(name = "symbolSearchData")
    public Object[][] symbolSearchData() {
        return new Object[][] {
                {"IBM", "IBM"},
                {"tesla", "TSLA"},
                {"microsoft", "MSFT"}
        };
    }

    /**
     * TC-01: Verifies a successful search for various known symbols.
     * This is a data-driven test that uses the 'symbolSearchData' provider.
     * It validates status code, response time, JSON schema, and the presence of the expected symbol.
     */
    @Test(dataProvider = "symbolSearchData", description = "TC-01: Verifies a successful search for a known symbol")
    public void testSuccessfulSymbolSearch(String keyword, String expectedSymbol) {
        log.info("Starting test with keyword: {} and expected symbol: {}", keyword, expectedSymbol);

        SymbolSearchApiClient client = new SymbolSearchApiClient();
        Response response = client.searchForSymbol(apiKey, keyword);

        log.debug("API Response Body for keyword '{}':\n{}", keyword, response.asPrettyString());

        response.then()
                // Assert 1: The status code is 200 OK
                .statusCode(200)

                // Assert 2: The response body structure matches our JSON schema
                .body(matchesJsonSchemaInClasspath("jsonschema/symbol-search-schema.json"))

                // Assert 3: The response time is within an acceptable threshold (e.g., less than 5 seconds)
                .time(lessThan(5000L))

                // Assert 4: The expected symbol is present SOMEWHERE in the list of results
                .body("bestMatches.'1. symbol'", hasItem(expectedSymbol));

        log.info("Test for keyword '{}' passed all validations.", keyword);
        pause(2);
    }

    /**
     * TC-02: Verifies that a search for a keyword with no matches returns a 200 OK status
     * and an empty list of results, as per the API contract.
     */
    @Test(description = "TC-02: Verifies a search for a keyword with no matches returns an empty list")
    public void testSearchWithNoResults() {
        log.info("Starting test for a search with no expected results.");

        SymbolSearchApiClient client = new SymbolSearchApiClient();
        String keywordWithNoMatches = "NoCompanyNameMatchesThis12345";
        Response response = client.searchForSymbol(apiKey, keywordWithNoMatches);

        log.debug("API Response Body for no results:\n{}", response.asPrettyString());

        response.then()
                // Assert 1: The status code is still 200 OK
                .statusCode(200)

                // Assert 2: The "bestMatches" array in the response body is empty
                .body("bestMatches", empty());

        log.info("Test for a search with no results passed all validations.");
        pause(2);
    }

    /**
     * Pauses execution after all tests in this class are finished
     * to respect the API rate limit before the next test class runs.
     */
    @AfterClass(alwaysRun = true)
    public void pauseAfterTests() {
        log.info("Pausing for 15 seconds before the next test class to respect API rate limit.");
        pause(15);
    }
}