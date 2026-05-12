package com.internship.tool.service;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.client.SimpleClientHttpRequestFactory;

import java.util.HashMap;
import java.util.Map;

@Service
public class AiServiceClient {

    private final RestTemplate restTemplate;
    private final String BASE_URL = "http://127.0.0.1:5000";

    public AiServiceClient() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();

        // ⏱ Timeout 10 seconds
        factory.setConnectTimeout(10000);
        factory.setReadTimeout(10000);

        this.restTemplate = new RestTemplate(factory);
    }

    // 🔹 CALL GENERATE REPORT
    public String generateReport(String input) {
        try {
            String url = BASE_URL + "/generate-report";

            Map<String, String> body = new HashMap<>();
            body.put("input", input);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, String>> request =
                    new HttpEntity<>(body, headers);

            ResponseEntity<String> response =
                    restTemplate.postForEntity(url, request, String.class);

            return response.getBody();

        } catch (Exception e) {
            System.out.println("AI ERROR: " + e.getMessage());
            return null; // required
        }
    }

    // 🔹 HEALTH CHECK
    public String checkHealth() {
        try {
            String url = BASE_URL + "/health";

            ResponseEntity<String> response =
                    restTemplate.getForEntity(url, String.class);

            return response.getBody();

        } catch (Exception e) {
            System.out.println("Health check failed");
            return null;
        }
    }
}