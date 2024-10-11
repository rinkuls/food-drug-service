package com.health.fooddrugs.service;


import com.health.fooddrugs.model.DrugRecord;

import java.util.List;
import java.util.Map;

public interface FdaService {
    Map<String, Object> searchDrugRecordRecords(String manufacturerName, int page, int size);

    DrugRecord saveDrugRecordApplication(DrugRecord DrugRecordRecord);

    List<DrugRecord> getAllStoredApplications();
}
