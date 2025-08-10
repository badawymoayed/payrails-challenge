package com.payrails.challenge.api;

// These are the import statements IntelliJ will add for you.
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

/**
 * This class is our API client for the Symbol Search endpoint.
 * It handles the technical details of creating and sending the request.
 */
public class SymbolSearchApiClient {

    /**
     * Performs a search for a given keyword.
     * @param apiKey The API key for authentication.
     * @param keyword The term to search for (e.g., "Tesla").
     * @return The full API response object.
     */
    public Response searchForSymbol(String apiKey, String keyword) {
        return given()
                // Using .log().uri() is great for debugging to see the exact URL being called
                .log().uri()
                .queryParam("function", "SYMBOL_SEARCH")
                .queryParam("apikey", apiKey)
                .queryParam("keywords", keyword).
                when()
                .get("/query").
                then()
                // Using .log().body() is useful for debugging to see the response
                .log().body()
                .extract()
                .response();
    }
}