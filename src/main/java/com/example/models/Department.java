package com.example.models;


import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name="department")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;


//    @OneToMany()
//    @JoinTable(
//            name = "department_teachers",
//            joinColumns = {@JoinColumn (name = "department_id")},
//            inverseJoinColumns = {@JoinColumn(name = "teacher_id")}
//    )

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "department_id")
    private List<Teacher> teachers;

    public Department() {
    }

    public Department(String name) {
        this.name = name;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<Teacher> teachers) {
        this.teachers = teachers;
    }
}
