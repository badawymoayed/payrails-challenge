package com.payrails.challenge.api;

import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

/**
 * API Client for interacting with the AlphaVantage Time Series endpoints.
 * This class encapsulates all the technical details for making these API calls.
 */
public class TimeSeriesApiClient {

    /**
     * Fetches time series data for a given stock symbol and function.
     * @param function The time series function to call (e.g., "TIME_SERIES_DAILY", "TIME_SERIES_WEEKLY").
     * @param symbol The stock symbol to query (e.g., "IBM").
     * @param apiKey The API key for authentication.
     * @return The full API response object.
     */
    public Response getTimeSeries(String function, String symbol, String apiKey) {
        return given()
                .log().uri() // Log the request URI for debugging
                .queryParam("function", function)
                .queryParam("symbol", symbol)
                .queryParam("apikey", apiKey)
                .when()
                .get("/query")
                .then()
                .log().body() // Log the response body for debugging
                .extract().response();
    }
}