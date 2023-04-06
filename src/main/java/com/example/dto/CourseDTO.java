package com.example.dto;

import com.example.models.Faculty;
import com.example.models.Group;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class CourseDTO {
    private String number;
    private Faculty faculty;
    private Set<Group> groups = new HashSet<>();
    private String valueOnSite;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public Set<Group> getGroups() {
        return groups;
    }

    public String getValueOnSite() {
        return valueOnSite;
    }

    public void setValueOnSite(String valueOnSite) {
        this.valueOnSite = valueOnSite;
    }

    public void setGroups(Set<Group> groups) {
        this.groups = groups;
    }
}
