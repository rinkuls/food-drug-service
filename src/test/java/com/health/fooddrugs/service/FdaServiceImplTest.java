package com.health.fooddrugs.service;


import com.health.fooddrugs.model.DrugRecord;
import com.health.fooddrugs.repository.DrugRecordRepository;
import com.health.fooddrugs.service.impl.FdaServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class FdaServiceImplTest {

    @Autowired
    private FdaServiceImpl fdaService;

    @Autowired
    private DrugRecordRepository drugRecordRepository;

    @Test
    public void testSaveDrugRecordRecord() {
        DrugRecord record = new DrugRecord();
        record.setApplicationNumber("APP001");
        record.setManufacturerName("Pfizer");
        record.setSubstanceName("As");
        DrugRecord savedRecord = fdaService.saveDrugRecordApplication(record);
        assertEquals("APP001", savedRecord.getApplicationNumber());

    }

    @Test
    public void testGetAllDrugRecordRecords() {
        DrugRecord DrugRecordRecord1 = new DrugRecord("APP123", "Pfizer", "SubstanceX");
        DrugRecord DrugRecordRecord2 = new DrugRecord("APP124", "Moderna", "SubstanceY");
        drugRecordRepository.deleteAll();
        fdaService.saveDrugRecordApplication(DrugRecordRecord1);
        fdaService.saveDrugRecordApplication(DrugRecordRecord2);


        List<DrugRecord> records = fdaService.getAllStoredApplications();
        assertEquals(2, records.size());
    }

    @Test
    public void testSearchDrugRecordRecords() {
        String manufacturerName = "Aurobindo Pharma Limited";
        int page = 0;
        int size = 10;


        Map<String, Object> response = fdaService.searchDrugRecordRecords(manufacturerName, page, size);


        assertThat(response).isNotNull();
        assertThat(response.containsKey("results")).isTrue();


    }
}
