package com.health.fooddrugs.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.health.fooddrugs.model.DrugRecord;
import com.health.fooddrugs.service.impl.FdaServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class FdaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FdaServiceImpl fdaService;

    @Test
    public void testStoreDrugRecordRecordWithStatusOk() throws Exception {
        DrugRecord drugRecord = new DrugRecord();
        drugRecord.setApplicationNumber("A02");
        drugRecord.setManufacturerName("Moderna");
        drugRecord.setSubstanceName("Vac");

        when(fdaService.saveDrugRecordApplication(drugRecord)).thenReturn(drugRecord);

        mockMvc.perform(post("/api/fda/store")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(drugRecord)))
                .andExpect(status().isOk());
    }


    @Test
    public void testStoreDrugRecordRecord() throws Exception {
        DrugRecord drugRecord = new DrugRecord("P123", "Pfizer", "Sub");
        Mockito.when(fdaService.saveDrugRecordApplication(Mockito.any(DrugRecord.class))).thenReturn(drugRecord);

        mockMvc.perform(post("/api/fda/store")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(drugRecord)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.applicationNumber").value("P123"));
    }

    @Test
    public void testGetAllDrugRecordRecords() throws Exception {
        DrugRecord drugRecord = new DrugRecord("12123", "test", "test1");
        Mockito.when(fdaService.getAllStoredApplications()).thenReturn(Collections.singletonList(drugRecord));

        mockMvc.perform(get("/api/fda/records"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].applicationNumber").value("12123"));
    }


}
