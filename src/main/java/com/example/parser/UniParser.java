package com.example.parser;

import com.example.models.Course;
import com.example.models.Faculty;
import com.example.models.Group;
import com.example.models.University;
import com.example.services.CourseService;
import com.example.services.FacultyService;
import com.example.services.GroupService;
import com.example.services.UniversityService;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.io.IOException;
import java.util.Objects;


@Component
public class UniParser {
    private final FacultyService facultyService;
    private final UniversityService universityService;
    private final CourseService courseService;

    private final GroupService groupService;

    @Autowired
    UniParser(FacultyService facultyService, UniversityService universityService, CourseService courseService, GroupService groupService) {
        this.facultyService = facultyService;
        this.universityService = universityService;
        this.courseService = courseService;
        this.groupService = groupService;
    }

    public void parseNecessaryInfoForUniversity(String uniUrl) throws IOException {
        Document doc = getConnection(uniUrl).get();

        University university = universityService.getOrCreateUniversityByLink(uniUrl);
        if (university.getTitle() == null) {
            university.setTitle(doc.getElementsByClass("header").get(0).text());
            universityService.update(university);
        }

        Elements facultiesTitle = doc.select("select#timetableform-facultyid");

        for (Element faculty : facultiesTitle.select("option")) {
            String facultyTitle = faculty.text();

            if (facultyTitle.isEmpty()) {
                continue;
            }

            Faculty newFaculty = facultyService.getByTitleAndUniversity(facultyTitle, university)
                    .orElseGet(() -> facultyService.save(new Faculty(university, facultyTitle, faculty.val())));

//            parseCourse(uniUrl, newFaculty);
        }

        for (Faculty faculty : university.getFaculty()) {
            parseCourse(uniUrl, faculty);
        }

        System.out.println();
    }

    public void parseCourse(String uniUrl, Faculty faculty) throws IOException {
        Document doc = getConnection(uniUrl).data("TimeTableForm[facultyId]", faculty.getValueOnSite()).post();


        for (Element option : Objects.requireNonNull(doc.getElementById("timetableform-course")).select("option")) {
            String courseNumber = option.val();

            if (courseNumber.isEmpty()) {
                continue;
            }
            Course newCourse = courseService.getCourseByFacultyAndNumber(faculty, courseNumber)
                    .orElseGet(() -> courseService.save(new Course(courseNumber, faculty, option.val())));
        }

        for (Course course : faculty.getCourses()) {
            parseGroup(uniUrl, course, faculty);
        }

//        faculty.getCourses().forEach(x -> System.out.println(x.getFaculty().getTitle() + " " + x.getNumber()));

    }


    public void parseGroup(String uniUrl, Course course, Faculty faculty) throws IOException {


        Document doc = getConnection(uniUrl)
                .data("TimeTableForm[facultyId]", faculty.getValueOnSite())
                .data("TimeTableForm[course]", course.getValueOnSite())
                .post();

        for (Element option : Objects.requireNonNull(doc.getElementById("timetableform-groupid")).select("option")) {
            String groupValue = option.val();

            if (groupValue.isEmpty()) {
                continue;
            }

            Group newGroup = groupService.getGroupByCourseAndValueOnSite(course, groupValue)
                    .orElseGet(() -> groupService.save(new Group(course, option.text(), groupValue)));
        }

    }

    private Connection getConnection(String url) throws IOException {
        Connection connection = Jsoup.connect(url).header("Connection", "keep-alive");
        String csrfToken = connection.get().select("meta[name=\"csrf-token\"]").attr("content");
        connection
                .header("Content-Type", "application/x-www-form-urlencoded")
                .data("_csrf-frontend", csrfToken);
        return connection;
    }
}
