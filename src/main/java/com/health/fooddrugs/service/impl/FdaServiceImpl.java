package com.health.fooddrugs.service.impl;


import com.health.fooddrugs.model.DrugRecord;
import com.health.fooddrugs.repository.DrugRecordRepository;
import com.health.fooddrugs.service.FdaService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class FdaServiceImpl implements FdaService {


    private static final String FDA_API_URL = "https://api.fda.gov/drug/drugsfda.json?search=openfda.manufacturer_name:";
    private static final Logger logger = LoggerFactory.getLogger(FdaServiceImpl.class);


    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DrugRecordRepository drugRecordRepository;

    /**
     * @param manufacturerName
     * @param page
     * @param size
     * @return Map<String, Object>
     */
    @Override
    public Map<String, Object> searchDrugRecordRecords(String manufacturerName, int page, int size) {
        String url = FDA_API_URL + manufacturerName + "&limit=" + size + "&skip=" + (page * size);
        logger.debug("*******************below is search result ************************+" + restTemplate.getForObject(url, Map.class));
        System.out.println("*******************below is search result ************************+" + restTemplate.getForObject(url, Map.class));

        return restTemplate.getForObject(url, Map.class);
    }

    /**
     * @param DrugRecord
     * @return DrugRecord
     */
    @Override
    public DrugRecord saveDrugRecordApplication(DrugRecord DrugRecord) {
        return drugRecordRepository.save(DrugRecord);
    }

    /**
     * @return list of DrugRecords
     */
    @Override
    public List<DrugRecord> getAllStoredApplications() {
        return drugRecordRepository.findAll();
    }
}
