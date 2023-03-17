package com.example.models;


import javax.persistence.*;

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
    @ManyToOne()
    @JoinColumn(name = "department_id")
    private Department department;

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


}
