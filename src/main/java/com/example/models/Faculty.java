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
    private String valueOnSite;

    public Faculty() {
    }

    public Faculty(University university, String title, String valueOnSite) {
        this.university = university;
        this.title = title;
        this.valueOnSite = valueOnSite;
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

    public String getValueOnSite() {
        return valueOnSite;
    }

    public void setValueOnSite(String valueOnSite) {
        this.valueOnSite = valueOnSite;
    }
}
