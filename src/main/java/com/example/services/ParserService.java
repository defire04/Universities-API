package com.example.services;

import com.example.dto.LessonItem;
import com.example.models.*;
import com.example.parser.UniParser;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

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
//        System.out.println(uniParser.parseSchedule(uniUrl, facultyValue, courseValue, groupValue));

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

        //            Teacher teacher = teacherService.findByFullName(lessonData[3]).orElseGet(() -> teacherService.save(new Teacher(lessonData[3])));
//            Subject subject = subjectService.findByTitleAndTeachersContaining(lessonData[0].replaceAll("\\[.*?\\]", ""), teacher)
//                    .orElseGet(() -> {
//                        Subject newSub = new Subject(lessonData[0].replaceAll("\\[.*?\\]", ""));
//                        newSub.getTeachers().add(teacher);
//                        return subjectService.save(newSub);
//                    });
//
//            Lesson lesson = lessonService.findBySubjectAndDate(subject, dataAndTime).orElseGet(() -> {
//                        Faculty faculty = facultyService.findByValueOnSite(facultyValue).orElseThrow(RuntimeException::new);
//                        Course course = courseService.findByFacultyAndValueOnSite(faculty, courseValue).orElseThrow(RuntimeException::new);
//
//                        return lessonService.save(new Lesson(course,
//                                faculty, subject, teacher, dataAndTime,
//                                lessonData[0].replaceAll("[^\\[\\]]*\\[([^\\[\\]]*)\\].*", "$1"),
//                                date
//                        ));
//                    });
    }
}
