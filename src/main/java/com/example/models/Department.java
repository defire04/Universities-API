package com.example.models;



import javax.persistence.*;
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
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "department_id")
    private List<Teacher> teachers;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
