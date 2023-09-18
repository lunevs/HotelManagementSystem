package com.ichtus.hotelmanagementsystem.units.controllers;

import com.ichtus.hotelmanagementsystem.model.dto.search.RequestHotelsSearch;
import com.ichtus.hotelmanagementsystem.model.dto.search.ResponseSearchResults;
import com.ichtus.hotelmanagementsystem.services.SearchService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class SearchControllerTest {

    @Value("${custom.rest.basePath}/search")
    String basePath;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SearchService searchService;

    @Test
    @WithMockUser
    void whenSearchHotels() throws Exception {
        ResponseSearchResults searchResults = new ResponseSearchResults()
                .setHotelId(1L)
                .setHotelName("test hotel");

        given(searchService.findAllHotelsByParameters(any(RequestHotelsSearch.class)))
                .willReturn(Collections.singletonList(searchResults));

        mockMvc.perform(post(basePath)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"searchCity\":\"Barcelona\"," +
                                "\"startDate\":\"2026-09-18\"," +
                                "\"endDate\":\"2026-09-23\"," +
                                "\"neededCapacity\":\"1\"," +
                                "\"minPrice\":\"1\"," +
                                "\"maxPrice\":\"10\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    @WithMockUser
    void whenSearchHotels_IncorrectInput() throws Exception {

        mockMvc.perform(post(basePath)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"searchCity\":\"\"," +
                                "\"startDate\":\"2021-09-18\"," +
                                "\"endDate\":\"2021-09-23\"," +
                                "\"neededCapacity\":\"100\"," +
                                "\"minPrice\":\"-1\"," +
                                "\"maxPrice\":\"-10\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.neededCapacity").exists())
                .andExpect(jsonPath("$.errors.minPrice").exists())
                .andExpect(jsonPath("$.errors.maxPrice").exists())
                .andExpect(jsonPath("$.errors.startDate").exists())
                .andExpect(jsonPath("$.errors.endDate").exists())
                .andExpect(jsonPath("$.errors.searchCity").doesNotExist());
    }
}
