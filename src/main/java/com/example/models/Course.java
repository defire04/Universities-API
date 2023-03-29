package com.example.models;


import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "course")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "number")
    private String number;

    @ManyToOne
    @JoinColumn(name = "faculty_id")
    private Faculty faculty;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private Set<Group> groups = new HashSet<>();

    @Column(name = "value")
    private String valueOnSite;

    public Course() {
    }

    public Course(String number, Faculty faculty, String valueOnSite) {
        this.number = number;
        this.faculty = faculty;
        this.valueOnSite = valueOnSite;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
}
