package com.health.fooddrugs.service;


import com.health.fooddrugs.model.DrugRecord;

import java.util.List;
import java.util.Map;

public interface FdaService {

    /**
     * @param manufacturerName
     * @param page
     * @param size
     * @return
     */


    Map<String, Object> searchDrugRecordRecords(String manufacturerName, int page, int size);

    /**
     * @param DrugRecordRecord
     * @return
     */
    DrugRecord saveDrugRecordApplication(DrugRecord DrugRecordRecord);

    /**
     * @return
     */
    List<DrugRecord> getAllStoredApplications();
}
