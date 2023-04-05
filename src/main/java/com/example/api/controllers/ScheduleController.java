package com.example.api.controllers;

import com.example.models.Lesson;
import com.example.parser.service.ParserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController

public class ScheduleController {

    private final ParserService parserService;

    @Autowired
    public ScheduleController(final ParserService parserService) {
        this.parserService = parserService;
    }

    @GetMapping("/schedule")
    public List<Lesson> scheduleByParam(@RequestParam() String university, @RequestParam() String faculty,
                                        @RequestParam() String course, @RequestParam() String group) throws IOException {
        return parserService.parseSchedule(university, faculty, course, group);
    }
}
