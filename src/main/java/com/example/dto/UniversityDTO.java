package com.example.dto;

import com.example.models.Department;
import com.example.models.Faculty;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

public class UniversityDTO {
    private String link;
    private String title;
//    private List<Department> departments;
    private List<Faculty> faculty;

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
//    public List<Department> getDepartments() {
//        return departments;
//    }
//
//    public void setDepartments(List<Department> departments) {
//        this.departments = departments;
//    }
}
