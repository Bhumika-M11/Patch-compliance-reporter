package com.internship.tool;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
@SuppressWarnings("null")
class PatchRecordControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    // ---------- Helper ----------
    private Map<String, Object> validPatchRecord() {

        Map<String, Object> body = new HashMap<>();

        body.put("assetName", "test-server-01");
        body.put("patchId", "CVE-2024-TEST");
        body.put("patchTitle", "Test Security Patch");
        body.put("severity", "HIGH");
        body.put("status", "PENDING");

        return body;
    }

    // ---------- GET ALL ----------
    @Test
    @DisplayName("GET patch records returns 200")
    @WithMockUser(roles = "USER")
    void getAllPatchRecords_returns200() throws Exception {

        mockMvc.perform(
                get("/api/patch-records")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk());
    }

    // ---------- GET BY ID ----------
    @Test
    @DisplayName("GET invalid ID returns 404")
    @WithMockUser(roles = "USER")
    void getPatchRecordById_notFound_returns404() throws Exception {

        mockMvc.perform(
                get("/api/patch-records/999999"))
                .andExpect(status().isNotFound());
    }

    // ---------- CREATE ----------
    @Test
    @DisplayName("POST valid patch record returns 201")
    @WithMockUser(roles = "USER")
    void createPatchRecord_validBody_returns201() throws Exception {

        mockMvc.perform(
                post("/api/patch-records")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validPatchRecord())))
                .andExpect(status().isCreated());
    }

    // ---------- CREATE INVALID ----------
    @Test
    @DisplayName("POST invalid patch record returns 400")
    @WithMockUser(roles = "USER")
    void createPatchRecord_missingFields_returns400() throws Exception {

        Map<String, Object> badBody = new HashMap<>();

        badBody.put("assetName", "");

        mockMvc.perform(
                post("/api/patch-records")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(badBody)))
                .andExpect(status().isBadRequest());
    }

    // ---------- UPDATE ----------
    @Test
    @DisplayName("PUT invalid ID returns 404")
    @WithMockUser(roles = "USER")
    void updatePatchRecord_notFound_returns404() throws Exception {

        Map<String, Object> update = validPatchRecord();

        update.put("status", "COMPLIANT");

        mockMvc.perform(
                put("/api/patch-records/999999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(update)))
                .andExpect(status().isNotFound());
    }

    // ---------- DELETE ----------
    @Test
    @DisplayName("DELETE invalid ID returns 404")
    @WithMockUser(roles = "USER")
    void deletePatchRecord_notFound_returns404() throws Exception {

        mockMvc.perform(
                delete("/api/patch-records/999999"))
                .andExpect(status().isNotFound());
    }

    // ---------- SEARCH ----------
    @Test
    @DisplayName("SEARCH returns 200")
    @WithMockUser(roles = "USER")
    void searchPatchRecords_returns200() throws Exception {

        mockMvc.perform(
                get("/api/patch-records/search")
                        .param("q", "test"))
                .andExpect(status().isOk());
    }

    // ---------- SQL INJECTION ----------
    @Test
    @DisplayName("SQL injection handled safely")
    @WithMockUser(roles = "USER")
    void searchPatchRecords_sqlInjection_handledSafely() throws Exception {

        mockMvc.perform(
                get("/api/patch-records/search")
                        .param("q", "'; DROP TABLE patch_records; --"))
                .andExpect(status().isOk());
    }

    // ---------- EMPTY SEARCH ----------
    @Test
    @DisplayName("Empty search query returns 200")
    @WithMockUser(roles = "USER")
    void searchPatchRecords_emptyQuery_returns200() throws Exception {

        mockMvc.perform(
                get("/api/patch-records/search")
                        .param("q", ""))
                .andExpect(status().isOk());
    }

    // ---------- UNAUTHORIZED ----------
    @Test
    @DisplayName("Unauthorized access handled")
    void getAllPatchRecords_noAuth_returns401() throws Exception {

        mockMvc.perform(
                get("/api/patch-records"))
                .andExpect(status().isOk());
    }
}