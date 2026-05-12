package com.internship.tool.controller;

import com.internship.tool.service.AiServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private AiServiceClient aiServiceClient;

    @GetMapping("/ai")
    public String testAI(@RequestParam String input) {
        return aiServiceClient.generateReport(input);
    }
    }