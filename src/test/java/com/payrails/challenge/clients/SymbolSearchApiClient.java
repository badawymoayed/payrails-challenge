package com.payrails.challenge.clients;

import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class SymbolSearchApiClient {

    public Response searchSymbol(String keywords, String apiKey) {
        return given()
                .queryParam("function", "SYMBOL_SEARCH")
                .queryParam("keywords", keywords)
                .queryParam("apikey", apiKey)
                .when()
                .get("/query");
    }
}