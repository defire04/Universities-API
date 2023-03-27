package com.example.models;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="faculty")
public class Faculty {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "university_id")
    private University university;

    @Column(name = "title")
    private String title;

    @OneToMany
    @JoinColumn(name = "faculty_id")
    private Set<Course> courses = new HashSet<>();

    @Column(name ="value")
    private String value;

    public Faculty() {
    }

    public Faculty(University university, String title, String value) {
        this.university = university;
        this.title = title;
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public University getUniversity() {
        return university;
    }

    public void setUniversity(University university) {
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
