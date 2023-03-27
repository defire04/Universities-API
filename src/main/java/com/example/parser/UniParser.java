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
import java.util.Objects;
import java.util.Optional;

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
        System.out.println("11111");
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
            if (faculty.val().isEmpty()) {
                continue;
            }

            String facultyTitle = faculty.text();


            Faculty newFaculty = facultyService.getByTitleAndUniversity(facultyTitle, university)
                    .orElseGet(() -> facultyService.save(new Faculty(university, facultyTitle, faculty.val())));

            parseCourse(uniUrl, newFaculty);

        }
    }

    public void parseCourse(String uniUrl, Faculty faculty) throws IOException {
        Document doc = getConnection(uniUrl).data("TimeTableForm[facultyId]", faculty.getValue()).post();


        Objects.requireNonNull(doc.getElementById("timetableform-course")).select("option")
                .forEach(option -> {
                    String courseNumber = option.val();
                    if (!courseNumber.isEmpty()) {
                        Course newCourse = courseService.getCourseByFacultyAndNumber(faculty, courseNumber)
                                .orElseGet(() -> courseService.save(new Course(courseNumber, faculty)));
                        faculty.getCourses().add(newCourse);
                    }
                });


    }


    public void parseGroup(String uniUrl, String facultyId, String course) {

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
