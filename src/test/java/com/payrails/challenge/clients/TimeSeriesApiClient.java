package com.payrails.challenge.clients;

import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class TimeSeriesApiClient {

    public Response getTimeSeries(String function, String symbol, String apiKey) {
        return given()
                .queryParam("function", function)
                .queryParam("symbol", symbol)
                .queryParam("apikey", apiKey)
                .when()
                .get("/query");
    }
}