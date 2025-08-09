package com.payrails.challenge.tests;

import com.payrails.challenge.clients.SymbolSearchApiClient;
import com.payrails.challenge.core.BaseTest;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.lessThan;
import static org.testng.Assert.assertEquals;

public class SymbolSearchTests extends BaseTest {

    private static final Logger log = LoggerFactory.getLogger(SymbolSearchTests.class);

    @DataProvider(name = "symbolSearchData")
    public Object[][] symbolSearchData() {
        return new Object[][] {
                {"IBM", "IBM"},
                {"tesla", "TSLA"},
                {"microsoft", "MSFT"}
        };
    }

    @Test(dataProvider = "symbolSearchData")
    public void testSuccessfulSymbolSearch(String keyword, String expectedSymbol) {
        log.info("Starting test with keyword: {} and expected symbol: {}", keyword, expectedSymbol);

        SymbolSearchApiClient client = new SymbolSearchApiClient();
        Response response = client.searchSymbol(keyword, apiKey);

        log.debug("API Response Body:\n{}", response.asPrettyString());

        // Assert 1: The status code is 200 OK
        assertEquals(response.getStatusCode(), 200);

        // Assert 2: The response body structure matches our JSON schema
        response.then().body(matchesJsonSchemaInClasspath("jsonschema/symbol-search-schema.json"));

        // Assert 3: The response time is within an acceptable threshold (e.g., less than 5 seconds)
        response.then().time(lessThan(5000L));

        // Assert 4: The expected symbol is present SOMEWHERE in the list of results
        response.then().body("bestMatches.\"1. symbol\"", hasItem(expectedSymbol));

        log.info("Test for keyword '{}' passed all validations.", keyword);
    }
}