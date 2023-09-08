package com.ichtus.hotelmanagementsystem.controllers;

import com.ichtus.hotelmanagementsystem.model.anotations.IsUser;
import com.ichtus.hotelmanagementsystem.model.dto.search.RequestHotelsSearch;
import com.ichtus.hotelmanagementsystem.services.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/search")
public class SearchController {

    private final SearchService searchService;

    @PostMapping
    @IsUser
    public ResponseEntity<?> searchHotels(@RequestBody RequestHotelsSearch searchParameters) {
        return ResponseEntity.ok(searchService.findAllHotelsByParameters(searchParameters));
    }

}
