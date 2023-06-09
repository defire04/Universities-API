package com.example.models;


//import javax.persistence.*;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "teacher")
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "full_name")
    private String fullName;


//    @ManyToOne
//    @JoinTable(
//            name = "department_teachers",
//            joinColumns = {@JoinColumn(name = "department_id")},
//            inverseJoinColumns = {@JoinColumn(name = "teacher_id")}
//    )
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;

    @ManyToMany(mappedBy = "teachers", fetch = FetchType.LAZY)
    private List<Subject> subjects = new ArrayList<>();

    public Teacher() {
    }

    public Teacher(String fullName) {
        this.fullName = fullName;
    }

    public Teacher(String fullName, Department department) {
        this.fullName = fullName;
        this.department = department;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}
