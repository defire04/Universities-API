package com.example.parser;

import com.example.dto.LessonItem;
import com.example.models.*;
import com.example.services.*;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;


@Component
public class UniParser {
    private final FacultyService facultyService;
    private final UniversityService universityService;
    private final CourseService courseService;
    private final GroupService groupService;
    private final SubjectService subjectService;
    private final LessonService lessonService;
    private final TeacherService teacherService;

    @Autowired
    UniParser(FacultyService facultyService, UniversityService universityService, CourseService courseService, GroupService groupService, SubjectService subjectService, LessonService lessonService, TeacherService teacherService) {
        this.facultyService = facultyService;
        this.universityService = universityService;
        this.courseService = courseService;
        this.groupService = groupService;
        this.subjectService = subjectService;
        this.lessonService = lessonService;
        this.teacherService = teacherService;
    }

    public University registerUniversity(String uniUrl) throws IOException {
        Document doc = getConnection(uniUrl).get();
        return new University(uniUrl, doc.getElementsByClass("header").get(0).text());
    }

    public List<Faculty> parseFaculty(University university) throws IOException {
        Document doc = getConnection(university.getLink()).get();

        List<Faculty> resultList = new ArrayList<>();

        Elements facultiesTitle = doc.select("select#timetableform-facultyid");

        for (Element faculty : facultiesTitle.select("option")) {
            String facultyTitle = faculty.text();
            String facultyVal = faculty.val();

            if (facultyVal.isEmpty()) {
                continue;
            }
            resultList.add(new Faculty(university, facultyTitle, facultyVal));
        }
        return resultList;
    }

    public List<Course> parseCourse(University university, Faculty faculty) throws IOException {
        Document doc = getConnection(university.getLink()).data("TimeTableForm[facultyId]", faculty.getValueOnSite()).post();
        List<Course> resultList = new ArrayList<>();

        for (Element option : Objects.requireNonNull(doc.getElementById("timetableform-course")).select("option")) {
            String courseNumber = option.val();

            if (courseNumber.isEmpty()) {
                continue;
            }

            resultList.add(new Course(courseNumber, faculty, option.val()));

        }

        return resultList;
    }


    public List<Group> parseGroup(University university, Course course, Faculty faculty) throws IOException {
        List<Group> resultList = new ArrayList<>();
        Document doc = getConnection(university.getLink())
                .data("TimeTableForm[facultyId]", faculty.getValueOnSite())
                .data("TimeTableForm[course]", course.getValueOnSite())
                .post();

        for (Element option : Objects.requireNonNull(doc.getElementById("timetableform-groupid")).select("option")) {
            String groupValue = option.val();

            if (groupValue.isEmpty()) {
                continue;
            }

            resultList.add(new Group(course, option.text(), groupValue));

        }


        return resultList;
    }


    public List<LessonItem> parseSchedule(String uniUrl, String facultyValue, String courseValue, String groupValue) throws IOException {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate now = LocalDate.now();
        String featureDate = now.plusDays(7).format(formatter);
        String pastDate = now.minusDays(14).format(formatter);

        Document doc = getConnection(uniUrl)
                .data("TimeTableForm[facultyId]", facultyValue)
                .data("TimeTableForm[course]", courseValue)
                .data("TimeTableForm[groupId]", groupValue)
                .data("date-picker", pastDate + " - " + featureDate)
                .data("TimeTableForm[dateStart]", pastDate)
                .data("TimeTableForm[dateEnd]", featureDate)
                .data("TimeTableForm[indicationDays]", "5")
                .data("time-table-type", "1")
                .post();


        Elements elements = doc.select("[data-content]");

        List<LessonItem> lessonItems = new ArrayList<>();

        for (Element el : elements) {
            String[] lessonData = el.attr("data-content").split("<br>");
            String dataAndTime = el.attr("title");

            LocalDate date = LocalDate.parse(dataAndTime.split(" ")[0], formatter);

            LessonItem lessonItem = new LessonItem();
            lessonItem.setTeacher(lessonData[3]);
            lessonItem.setSubject(lessonData[0].replaceAll("\\[.*?\\]", ""));
            lessonItem.setFaculty(facultyValue);
            lessonItem.setCourse(courseValue);
            lessonItem.setLessonDate(date);
            lessonItem.setPairNumber(dataAndTime.split(" ")[1]);


            lessonItems.add(lessonItem);


        }


        return lessonItems;
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
