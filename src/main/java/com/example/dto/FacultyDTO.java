package com.example.dto;

import com.example.models.Course;
import com.example.models.University;


import java.util.HashSet;
import java.util.Set;

public class FacultyDTO {
    private UniversityDTOWithOutFacultyAndDepartments university;
    private String title;
    private Set<Course> courses = new HashSet<>();
    private String valueOnSite;

    public UniversityDTOWithOutFacultyAndDepartments getUniversity() {
        return university;
    }

    public void setUniversity(UniversityDTOWithOutFacultyAndDepartments university) {
        this.university = university;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    public String getValueOnSite() {
        return valueOnSite;
    }

    public void setValueOnSite(String valueOnSite) {
        this.valueOnSite = valueOnSite;
    }
}
