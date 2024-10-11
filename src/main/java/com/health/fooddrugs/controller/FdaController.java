package com.health.fooddrugs.controller;


import com.health.fooddrugs.model.DrugRecord;
import com.health.fooddrugs.service.impl.FdaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/fda")
public class FdaController {

    @Autowired
    private FdaServiceImpl FdaService;


    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> searchDrugRecordApplications(
            @RequestParam String manufacturerName,
            @RequestParam int page,
            @RequestParam int size) {

        Map<String, Object> result = FdaService.searchDrugRecordRecords(manufacturerName, page, size);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/store")
    public DrugRecord storeDrugRecordApplication(@RequestBody @Valid DrugRecord DrugRecordApplication) {
        return FdaService.saveDrugRecordApplication(DrugRecordApplication);
    }


    @GetMapping("/records")
    public List<DrugRecord> getAllStoredApplications() {
        return FdaService.getAllStoredApplications();
    }
}

