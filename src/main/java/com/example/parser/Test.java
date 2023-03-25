package com.example.parser;

import com.example.models.Faculty;
import com.example.models.University;
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


public class Test {


    public static void main(String[] args) throws Exception {


//        String url = "https://e-rozklad.dut.edu.ua/time-table/group?type=0";
//        Connection connection = Jsoup.connect(url).header("Connection", "keep-alive");
//        String csrfToken = connection.get().select("meta[name=\"csrf-token\"]").attr("content");
//
//        connection
//                .header("Content-Type", "application/x-www-form-urlencoded")
//                .data("_csrf-frontend", csrfToken)
//                .data("TimeTableForm[facultyId]", "1")
//                .data("TimeTableForm[course]", "2")
//                .data("TimeTableForm[groupId]", "1545");
//
//        Document doc = connection.post();
//
//        Elements elements = doc.select("[data-content]");
//
//        for (Element el : elements) {
//
//            System.out.println(el.attr("data-content"));
//            System.out.println(el.attr("title"));
//        }
//        Parser.parseFaculty(new University("https://e-rozklad.dut.edu.ua/time-table/group?type=0", "ДУТ"));
    }
}


//@Component
//class Parser {

//    private final FacultyService facultyService;
//    private final UniversityService universityService;
//
//    @Autowired
//    Parser(FacultyService facultyService, UniversityService universityService) {
//        this.facultyService = facultyService;
//        this.universityService = universityService;
//    }
//
//    public void parseNecessaryInfoForUniversity(String uniUrl){
//        if(universityService.existsByLink(uniUrl)){
//
//        }
//    }
//
//    public  void parseFaculty(University university) throws IOException {
//        Connection connection = getConnection(university.getLink());
//
//        Document doc = connection.get();
//
//        Elements elements = doc.select("select#timetableform-facultyid");
//
//
//        for (Element element : elements.select("option")) {
//            if(!element.val().isEmpty()){
//                String facultyName = element.text();
//                System.out.println(facultyName);
//                facultyService.save(new Faculty(university, facultyName));
//            }
//
//        }
//
//    }
//
//
//
//    public void parseSchedule(String uniUrl, String facultyId, String course ){
//
//    }
//
//    public Connection getConnection(String url) {
//        return Jsoup.connect(url).header("Connection", "keep-alive");
//    }
//}