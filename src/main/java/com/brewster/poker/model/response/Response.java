package com.brewster.poker.model.response;

import java.util.HashMap;
import java.util.Map;

public class Response {
    private final String body;
    private final Map<String, String> headers;
    private final int statusCode;

    public Response(final String body, final int statusCode){
        this.statusCode = statusCode;
        this.body = body;
        this.headers = createHeaders();
    }

    public String getBody() {
        return body;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public int getStatusCode() {
        return statusCode;
    }

    private Map<String, String> createHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Access-Control-Allow-Origin", "*");
        headers.put("Access-Control-Allow-Methods", "*");
        headers.put("Access-Control-Allow-Headers", "*");
        return headers;
    }
}
