package com.health.fooddrugs.repository;


import com.health.fooddrugs.model.DrugRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class DrugRecordRepositoryTest {

    @Autowired
    private DrugRecordRepository drugRecordRecordRepository;


    @BeforeEach
    public void setUp() {
        DrugRecord drugRecordRecord = new DrugRecord("3456", "P1", "X");
        drugRecordRecordRepository.save(drugRecordRecord);
    }

    @Test
    public void testFindById() {
        Optional<DrugRecord> foundRecord = drugRecordRecordRepository.findById("3456");
        assertTrue(foundRecord.isPresent());
        assertEquals("P1", foundRecord.get().getManufacturerName());
    }

    @Test
    public void testSave() {
        DrugRecord newRecord = new DrugRecord("24", "Moderna", "SY");
        DrugRecord savedRecord = drugRecordRecordRepository.save(newRecord);
        assertNotNull(savedRecord);
        assertEquals("24", savedRecord.getApplicationNumber());
    }

    @Test
    public void testDelete() {
        DrugRecord newRecord = new DrugRecord("APP124", "Moderna", "S2");
        drugRecordRecordRepository.save(newRecord);
        drugRecordRecordRepository.delete(newRecord);
        Optional<DrugRecord> foundRecord = drugRecordRecordRepository.findById("APP124");
        assertFalse(foundRecord.isPresent());
    }
}