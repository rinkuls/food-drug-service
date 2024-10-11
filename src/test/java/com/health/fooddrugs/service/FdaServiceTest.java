package com.health.fooddrugs.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

public class FdaServiceTest {
    @Mock
    private FdaService fdaService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void searchDrugRecordApplications_ShouldReturnListOfDrugRecordRecords_WhenManufacturerNameExists() {
        // Arrange
        String manufacturerName = "Pfizer";
        Map<String, Object> expectedResult = new HashMap<>();
        when(fdaService.searchDrugRecordRecords(manufacturerName, 1, 1))
                .thenReturn(expectedResult);

        // Act
        Map<String, Object> result = fdaService.searchDrugRecordRecords(manufacturerName, 1, 1);

        // Assert
        assertNotNull(result);

    }

    @Test
    void searchDrugRecordApplications_ShouldReturnEmptyList_WhenManufacturerNameDoesNotExist() {
        // Arrange
        String manufacturerName = "Nonexistent";

        when(fdaService.searchDrugRecordRecords(manufacturerName, 0, 0))
                .thenReturn(null);

        // Act
        Map<String, Object> result = fdaService.searchDrugRecordRecords(manufacturerName, 0, 0);

        // Assert
        assertNull(result);
    }
}
