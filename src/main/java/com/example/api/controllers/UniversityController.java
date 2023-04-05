package com.example.api.controllers;

import com.example.dto.FacultyDTO;
import com.example.dto.UniversityDTO;
import com.example.models.Faculty;
import com.example.models.University;
import com.example.parser.service.ParserService;
import com.example.services.FacultyService;
import com.example.services.UniversityService;
import com.example.services.mappers.FacultyMapperService;
import com.example.services.mappers.UniversityMapperService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/university")

public class UniversityController {

    private final UniversityService universityService;
    private final ParserService parserService;
    private final FacultyService facultyService;

    private final UniversityMapperService universityMapperService;
    private final FacultyMapperService facultyMapperService;

    @Autowired
    public UniversityController(UniversityService universityService, UniversityMapperService universityMapperService, ParserService parserService, FacultyService facultyService, FacultyMapperService facultyMapperService) {
        this.universityService = universityService;
        this.universityMapperService = universityMapperService;
        this.parserService = parserService;
        this.facultyService = facultyService;
        this.facultyMapperService = facultyMapperService;
    }

    @GetMapping("/getAll")
    public List<UniversityDTO> getAll() {
        return universityMapperService.convertUniversityListToUniversityDTOList(universityService.findAll());
    }

    @GetMapping("")
    public UniversityDTO universityByLink(@RequestParam() String university) {
        return universityMapperService.convertUniversityToUniversityDTO(universityService.findByLink(university).orElse(null));
    }

    @GetMapping("/registerOrUpdate")
    public UniversityDTO registerOrUpdate(@RequestParam() String university) throws IOException {
        return universityMapperService.convertUniversityToUniversityDTO(parserService.registerOrUpdateUniversityInfo(university));
    }

    @GetMapping("/getFacultiesByUniversity")
    public List<FacultyDTO> getFacultiesByUniversity(@RequestParam() String university) {
        return facultyMapperService.convertFacultyListToFacultyDTOList(facultyService.findByUniversity(universityService.findByLink(university)
                .orElseGet(University::new)));
    }
}
