package com.example.parser;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class Test {
    public static void main(String[] args) throws Exception {
        String url = "https://e-rozklad.dut.edu.ua/time-table/group?type=0";
        Connection connection  = Jsoup.connect(url).header("Connection", "keep-alive");
        String csrfToken = connection.get().select("meta[name=\"csrf-token\"]").attr("content");

        connection
                .header("Content-Type", "application/x-www-form-urlencoded")
                .data("_csrf-frontend", csrfToken)
                .data("TimeTableForm[facultyId]", "1")
                .data("TimeTableForm[course]", "2")
                .data("TimeTableForm[groupId]", "1545");

        Document doc = connection.post();

        Elements elements = doc.select("[data-content]");

        for (Element el : elements) {

            System.out.println(el.attr("data-content"));
            System.out.println(el.attr("title"));
        }
    }
}