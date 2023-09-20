package com.ichtus.hotelmanagementsystem.controllers;

import com.ichtus.hotelmanagementsystem.model.anotations.IsUser;
import com.ichtus.hotelmanagementsystem.model.dto.search.RequestHotelsSearch;
import com.ichtus.hotelmanagementsystem.model.dto.search.ResponseSearchResults;
import com.ichtus.hotelmanagementsystem.services.SearchService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller allows to search Hotels by parameters
 * @author smlunev
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/search")
public class SearchController {

    private final SearchService searchService;

    /**
     * Endpoint to search Hotels
     * @param searchParameters dto with search parameters
     * @return list ResponseSearchResults with hotels and rooms information
     */
    @PostMapping
    @IsUser
    public ResponseEntity<List<ResponseSearchResults>> searchHotels(@Valid @RequestBody RequestHotelsSearch searchParameters) {
        return ResponseEntity.ok(searchService.findAllHotelsByParameters(searchParameters));
    }

}
