package com.example.dto;

import com.example.models.Faculty;

import java.time.LocalDate;


public class UniversityDTOWithOutFacultyAndDepartments {
    private String link;
    private String title;
    private LocalDate lastUpdate;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDate lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
