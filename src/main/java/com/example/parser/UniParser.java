package com.example.parser;

import com.example.models.Course;
import com.example.models.Faculty;
import com.example.models.University;
import com.example.services.CourseService;
import com.example.services.FacultyService;
import com.example.services.UniversityService;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class UniParser {
    private final FacultyService facultyService;
    private final UniversityService universityService;

    private final CourseService courseService;

    @Autowired
    UniParser(FacultyService facultyService, UniversityService universityService, CourseService courseService) {
        this.facultyService = facultyService;
        this.universityService = universityService;
        this.courseService = courseService;
    }

    public void parseNecessaryInfoForUniversity(String uniUrl) throws IOException {
        Connection connection = getConnection(uniUrl);
        Document doc = connection.get();

        University university = universityService.getOrCreateUniversityByLink(uniUrl);
        if (university.getTitle() == null || university.getTitle().isEmpty()) {
            Elements divTitleHeader = doc.getElementsByClass("header");
            university.setTitle(divTitleHeader.get(0).text());

            universityService.update(university);
        }

        Elements facultiesTitle = doc.select("select#timetableform-facultyid");

        for (Element faculty : facultiesTitle.select("option")) {
            if (faculty.val().isEmpty()) {
                continue;
            }

            String facultyTitle = faculty.text();

            Faculty newFaculty = facultyService.getFacultyByTitle(facultyTitle)
                    .orElse(new Faculty(university, facultyTitle, faculty.val()));
            facultyService.save(newFaculty);


        }

        Elements courses = doc.select("timetableform-course");
        System.out.println(courses);
        for (Element course : courses) {
            if (course.val().isEmpty()) {
                continue;
            }
            //                courseService.save(new Course());
            System.out.println(course);
        }
    }

    public void parseFaculty(Document doc) throws IOException {

    }


    public void parseSchedule(String uniUrl, String facultyId, String course) {

    }

    private Connection getConnection(String url) {
        return Jsoup.connect(url).header("Connection", "keep-alive");
    }
}
