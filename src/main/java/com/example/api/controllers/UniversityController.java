package com.example.api.controllers;

import com.example.dto.CourseDTO;
import com.example.dto.FacultyDTO;
import com.example.dto.GroupDTO;
import com.example.dto.UniversityDTO;
import com.example.models.Course;
import com.example.models.Faculty;
import com.example.models.Group;
import com.example.models.University;
import com.example.parser.service.ParserService;
import com.example.services.CourseService;
import com.example.services.FacultyService;
import com.example.services.GroupService;
import com.example.services.UniversityService;
import com.example.services.mappers.CourseMapperService;
import com.example.services.mappers.FacultyMapperService;
import com.example.services.mappers.GroupMapperService;
import com.example.services.mappers.UniversityMapperService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
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
    private final CourseService courseService;
    private final GroupService groupService;
    private final UniversityMapperService universityMapperService;
    private final FacultyMapperService facultyMapperService;
    private final CourseMapperService courseMapperService;
    private final GroupMapperService groupMapperService;

    @Autowired
    public UniversityController(UniversityService universityService, UniversityMapperService universityMapperService, ParserService parserService, FacultyService facultyService, CourseService courseService, GroupService groupService, FacultyMapperService facultyMapperService, CourseMapperService courseMapperService, GroupMapperService groupMapperService) {
        this.universityService = universityService;
        this.universityMapperService = universityMapperService;
        this.parserService = parserService;
        this.facultyService = facultyService;
        this.courseService = courseService;
        this.groupService = groupService;
        this.facultyMapperService = facultyMapperService;
        this.courseMapperService = courseMapperService;
        this.groupMapperService = groupMapperService;
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

    @GetMapping("/getCourseByUniversityAndFacultyValue")
    public List<CourseDTO> getCourseByFaculty(@RequestParam() String university, @RequestParam() String facultyValue) throws ChangeSetPersister.NotFoundException {
        University foundUni = universityService.findByLink(university).orElseThrow(ChangeSetPersister.NotFoundException::new);

        return courseMapperService.convertCourseListToCourseDTOList(
                courseService.findByFaculty(facultyService.findByUniversityAndValueOnSite(foundUni, facultyValue).orElseThrow(ChangeSetPersister.NotFoundException::new))
        );
    }


    @GetMapping("/getGroupByUniversityAndFacultyValueAndCourseAndGroupValue")
    public GroupDTO getGroupByUniversityAndFacultyValueAndCourse(@RequestParam() String university, @RequestParam() String facultyValue,
                                                                 @RequestParam() String course, @RequestParam String groupValue) throws ChangeSetPersister.NotFoundException {
        University foundUni = universityService.findByLink(university).orElseThrow(ChangeSetPersister.NotFoundException::new);
        Faculty foundFaculty = facultyService.findByUniversityAndValueOnSite(foundUni, facultyValue).orElseThrow(ChangeSetPersister.NotFoundException::new);
        Course foundCourse = courseService.findCourseByFacultyAndNumber(foundFaculty, course).orElseThrow(ChangeSetPersister.NotFoundException::new);

        return groupMapperService.convertGroupToGroupDTO(groupService.findGroupByCourseAndValueOnSite(foundCourse, groupValue).orElseThrow(ChangeSetPersister.NotFoundException::new));
    }
}
