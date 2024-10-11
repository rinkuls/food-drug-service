package com.health.fooddrugs.repository;


import com.health.fooddrugs.model.DrugRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DrugRecordRepository extends JpaRepository<DrugRecord, String> {
}

