package com.example.dto;

import com.example.models.Department;
import com.example.models.Faculty;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UniversityDTO {

    private Long id;
    private String link;
    private String title;
//    private List<Department> departments;
    private List<Faculty> faculty;

    private LocalDate lastUpdate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public String getTitle() {
        return title;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Faculty> getFaculty() {
        return faculty;
    }

    public void setFaculty(List<Faculty> faculty) {
        this.faculty = faculty;
    }

    public LocalDate getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDate lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    //    public List<Department> getDepartments() {
//        return departments;
//    }
//
//    public void setDepartments(List<Department> departments) {
//        this.departments = departments;
//    }
}
