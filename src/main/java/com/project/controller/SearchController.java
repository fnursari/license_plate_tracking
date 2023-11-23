package com.project.controller;

import com.project.payload.response.SearchResponse;
import com.project.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;


    @GetMapping("/byPlate/{plate}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public SearchResponse searchByPlate(@PathVariable String plate){
        return searchService.searchByPlate(plate);

    }
}
