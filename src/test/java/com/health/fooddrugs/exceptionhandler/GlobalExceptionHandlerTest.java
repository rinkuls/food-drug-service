package com.health.fooddrugs.exceptionhandler;

import com.health.fooddrugs.controller.FdaController;
import com.health.fooddrugs.service.impl.FdaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.HttpClientErrorException;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FdaController.class)
public class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FdaServiceImpl fdaService;

    @BeforeEach
    public void setUp() {

        Mockito.reset(fdaService);
    }

    @Test
    public void handleNotFoundExceptionTest() throws Exception {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpClientErrorException notFoundException = HttpClientErrorException.create(
                HttpStatus.NOT_FOUND, "404 Not Found", headers, null, null);

        doThrow(notFoundException)
                .when(fdaService).searchDrugRecordRecords(Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt());


        mockMvc.perform(get("/api/fda/search?manufacturerName=NonExistent&size=10&page=0"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error", is("No matches found for the specified manufacturer.")))
                .andExpect(jsonPath("$.status", is(HttpStatus.NOT_FOUND.value())))
                .andExpect(jsonPath("$.message", is("404 404 Not Found")));
    }

    @Test
    public void handleHttpClientErrorExceptionTest() throws Exception {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpClientErrorException badRequestException = HttpClientErrorException.create(
                HttpStatus.BAD_REQUEST, "400 Bad Request", headers, null, null);

        doThrow(badRequestException)
                .when(fdaService).searchDrugRecordRecords(Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt());


        mockMvc.perform(get("/api/fda/search?manufacturerName=InvalidData&size=10&page=0"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", is("An error occurred while fetching data from the FDA API.")))
                .andExpect(jsonPath("$.status", is(HttpStatus.BAD_REQUEST.value())))
                .andExpect(jsonPath("$.message", is("400 400 Bad Request")));
    }

    @Test
    public void handleGenericExceptionTest() throws Exception {

        doThrow(new RuntimeException("Something went wrong!"))
                .when(fdaService).searchDrugRecordRecords(Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt());


        mockMvc.perform(get("/api/fda/search?manufacturerName=RandomError&size=10&page=0"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.error", is("An unexpected error occurred.")))
                .andExpect(jsonPath("$.status", is(HttpStatus.INTERNAL_SERVER_ERROR.value())))
                .andExpect(jsonPath("$.message", is("Something went wrong!")));
    }
}
