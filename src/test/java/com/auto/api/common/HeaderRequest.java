package com.auto.api.common;

import java.util.HashMap;
import java.util.Map;

public class HeaderRequest {
    String contentType;
    String accept;
    String authorization;
    public HeaderRequest() {
        this.contentType = "Content-Type";
        this.accept = "Accept";
        this.authorization = "Authorization";
    }

    public Map<String, String> headerMap() {
        Map<String, String> map = new HashMap<>();
        map.put(this.contentType, "application/json");
        return map;
    }
    public Map<String, String> headerMapAPI(String authorization) {
        Map<String, String> map = new HashMap<>();
        map.put(this.contentType, "application/json");
        map.put(this.contentType, "application/json");
        map.put(this.authorization, "Bearer " + authorization);
        return map;
    }
}
