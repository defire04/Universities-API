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

    public void parseNecessaryInfoForUniversity(String uniUrl) throws IOException {
        Document doc = getConnection(uniUrl).get();

        University university = universityService.findByLink(uniUrl).orElseGet(() ->
                universityService.save(new University(uniUrl)));
        if (university.getTitle() == null) {
            university.setTitle(doc.getElementsByClass("header").get(0).text());
            universityService.update(university);
        }

        Elements facultiesTitle = doc.select("select#timetableform-facultyid");

        for (Element faculty : facultiesTitle.select("option")) {
            String facultyTitle = faculty.text();
            String facultyVal = faculty.val();

            if (facultyVal.isEmpty()) {
                continue;
            }

            Faculty newFaculty = facultyService.findByTitleAndUniversity(facultyTitle, university)
                    .orElseGet(() -> facultyService.save(new Faculty(university, facultyTitle, facultyVal)));
        }


        for (Faculty faculty : university.getFaculty()) {
            parseCourse(uniUrl, faculty);
        }

    }

    private void parseCourse(String uniUrl, Faculty faculty) throws IOException {
        Document doc = getConnection(uniUrl).data("TimeTableForm[facultyId]", faculty.getValueOnSite()).post();

        for (Element option : Objects.requireNonNull(doc.getElementById("timetableform-course")).select("option")) {
            String courseNumber = option.val();

            if (courseNumber.isEmpty()) {
                continue;
            }
            Course newCourse = courseService.findCourseByFacultyAndNumber(faculty, courseNumber)
                    .orElseGet(() -> courseService.save(new Course(courseNumber, faculty, option.val())));
        }

        for (Course course : faculty.getCourses()) {
            parseGroup(uniUrl, course, faculty);
        }

//        faculty.getCourses().forEach(x -> System.out.println(x.getFaculty().getTitle() + " " + x.getNumber()));

    }


    private void parseGroup(String uniUrl, Course course, Faculty faculty) throws IOException {

        Document doc = getConnection(uniUrl)
                .data("TimeTableForm[facultyId]", faculty.getValueOnSite())
                .data("TimeTableForm[course]", course.getValueOnSite())
                .post();

        for (Element option : Objects.requireNonNull(doc.getElementById("timetableform-groupid")).select("option")) {
            String groupValue = option.val();

            if (groupValue.isEmpty()) {
                continue;
            }

            Group newGroup = groupService.findGroupByCourseAndValueOnSite(course, groupValue)
                    .orElseGet(() -> groupService.save(new Group(course, option.text(), groupValue)));
        }
    }


    public List<LessonItem> parseSchedule(String uniUrl, String facultyValue, String courseValue, String groupValue) throws IOException {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String now = dateFormat.format(calendar.getTime());
        calendar.add(Calendar.DAY_OF_YEAR, 7);
        String featureDate = dateFormat.format(calendar.getTime());
        calendar.add(Calendar.DAY_OF_YEAR, -14);
        String pastDate = dateFormat.format(calendar.getTime());

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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

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
