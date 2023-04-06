package com.example.parser.service;

import com.example.dto.LessonItem;
import com.example.models.*;
import com.example.parser.UniParser;
import com.example.services.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ParserService {
    private final UniParser uniParser;

    private final FacultyService facultyService;
    private final UniversityService universityService;
    private final CourseService courseService;
    private final GroupService groupService;
    private final SubjectService subjectService;
    private final LessonService lessonService;
    private final TeacherService teacherService;


    public ParserService(UniParser uniParser, FacultyService facultyService, UniversityService universityService, CourseService courseService, GroupService groupService, SubjectService subjectService, LessonService lessonService, TeacherService teacherService) {
        this.uniParser = uniParser;
        this.facultyService = facultyService;
        this.universityService = universityService;
        this.courseService = courseService;
        this.groupService = groupService;
        this.subjectService = subjectService;
        this.lessonService = lessonService;
        this.teacherService = teacherService;
    }

    public List<Lesson> parseSchedule(String uniUrl, String facultyValue, String courseValue, String groupValue) throws IOException {

        for (LessonItem lessonItem : uniParser.parseSchedule(uniUrl, facultyValue, courseValue, groupValue)) {
            Teacher teacher = teacherService.findByFullName(lessonItem.getTeacher()).orElseGet(() -> teacherService.save(new Teacher(lessonItem.getTeacher())));
            Subject subject = subjectService.findByTitleAndTeachersContaining(lessonItem.getSubject(), teacher)
                    .orElseGet(() -> {
                        Subject newSub = new Subject(lessonItem.getSubject());
                        newSub.getTeachers().add(teacher);
                        return subjectService.save(newSub);
                    });

            Lesson lesson = lessonService.findBySubjectAndDateAndPairNumber(subject, lessonItem.getLessonDate(), lessonItem.getPairNumber()).orElseGet(() -> {
                Faculty faculty = facultyService.findByValueOnSite(facultyValue).orElseThrow(RuntimeException::new);
                Course course = courseService.findByFacultyAndValueOnSite(faculty, courseValue).orElseThrow(RuntimeException::new);

                return lessonService.save(new Lesson(course,
                        faculty, subject, teacher, lessonItem.getSubject(),
                        lessonItem.getPairNumber(), lessonItem.getLessonDate()
                ));
            });
        }

        return lessonService.getAllLessons();
    }


    public University registerOrUpdateUniversityInfo(String uniUrl) throws IOException {

        Optional<University> universityOptional = universityService.findByLink(uniUrl);
        if (universityOptional.isPresent() && universityOptional.get().getLastUpdate().isAfter(LocalDate.now().minusDays(1)) ){
            return universityOptional.get();
        }

        University university = uniParser.registerUniversity(universityService.prepareUniversityLink(uniUrl));

        List<Faculty> faculties = uniParser.parseFaculty(universityService.save(university));

        for (Faculty faculty : facultyService.save(faculties)) {
            List<Course> coursesInFaculty = uniParser.parseCourse(university, faculty);
            for (Course course : courseService.save(coursesInFaculty)) {
                List<Group> groups = uniParser.parseGroup(university, course, faculty);
                groupService.save(groups);
            }
        }

        return universityService.findByLink(uniUrl).orElseGet(University::new);
    }


}
