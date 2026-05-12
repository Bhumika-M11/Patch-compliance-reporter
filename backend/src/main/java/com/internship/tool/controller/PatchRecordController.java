package com.internship.tool.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/patch-records")
public class PatchRecordController {

    private final List<Map<String, Object>> records = new ArrayList<>();

    // GET ALL
    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> getAllPatchRecords(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return ResponseEntity.ok(records);
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getPatchRecordById(@PathVariable Long id) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Patch record not found");
    }

    // CREATE
    @PostMapping
    public ResponseEntity<?> createPatchRecord(
            @RequestBody Map<String, Object> body) {

        if (!body.containsKey("assetName")
                || body.get("assetName").toString().isBlank()) {

            return ResponseEntity.badRequest()
                    .body("assetName is required");
        }

        body.put("id", 1);

        records.add(body);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(body);
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePatchRecord(
            @PathVariable Long id,
            @RequestBody Map<String, Object> body) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Patch record not found");
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePatchRecord(
            @PathVariable Long id) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Patch record not found");
    }

    // SEARCH
    @GetMapping("/search")
    public ResponseEntity<List<Map<String, Object>>> searchPatchRecords(
            @RequestParam String q) {

        return ResponseEntity.ok(records);
    }
}
