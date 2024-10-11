package com.health.fooddrugs.controller;


import com.health.fooddrugs.FoodDrugServiceApplication;
import com.health.fooddrugs.model.DrugRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = FoodDrugServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FdaControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String getBaseUrl(String endpoint) {
        return "http://localhost:" + port + "/api/fda" + endpoint;
    }

    @BeforeEach
    public void setUp() {
        // Prepare any setup if needed before each test case
    }

    @Test
    public void testSearchDrugRecordApplications() {
        // Call the search API
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getBaseUrl("/search"))
                .queryParam("manufacturerName", "Pfizer")
                .queryParam("page", 0)
                .queryParam("size", 10);

        ResponseEntity<Map> response = restTemplate.getForEntity(builder.toUriString(), Map.class);

        // Verify the response
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().containsKey("results")).isTrue();
    }

    @Test
    public void testStoreDrugRecordApplication() {
        // Create a new DrugRecord object
        DrugRecord newRecord = new DrugRecord();
        newRecord.setApplicationNumber("ANDA077374");
        newRecord.setManufacturerName("Pfizer");
        newRecord.setSubstanceName("Ibuprofen");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<DrugRecord> request = new HttpEntity<>(newRecord, headers);

        // Call the store API
        ResponseEntity<DrugRecord> response = restTemplate.postForEntity(getBaseUrl("/store"), request, DrugRecord.class);

        // Verify the response
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getManufacturerName()).isEqualTo("Pfizer");
        assertThat(response.getBody().getApplicationNumber()).isEqualTo("ANDA077374");
    }

    @Test
    public void testGetAllStoredApplications() {
        // Add a new record first
        testStoreDrugRecordApplication();

        // Call the records API
        ResponseEntity<List> response = restTemplate.getForEntity(getBaseUrl("/records"), List.class);

        // Verify the response
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().size()).isGreaterThan(0);
    }
}