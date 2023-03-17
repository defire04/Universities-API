package com.example.models;


import javax.persistence.*;

@Entity
public class University {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "link")
    private String link;

    @Column(name = "title")
    private String title;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
