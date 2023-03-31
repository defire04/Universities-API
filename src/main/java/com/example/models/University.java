package com.example.models;


//import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class University {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "link", nullable = false)
    private String link;

    @Column(name = "title")
    private String title;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "department_id")
    @JsonIgnoreProperties("university")
    private final List<Department> departments = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "university_id")
    @JsonIgnoreProperties("university")
    private final List<Faculty> faculty = new ArrayList<>();


    public University() {
    }

    public University(String link) {
        this.link = link;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Department> getDepartments() {
        return departments;
    }

    public List<Faculty> getFaculty() {
        return faculty;
    }

    @Override
    public boolean equals(Object o) {

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        University that = (University) o;
        if (!Objects.equals(id, that.id) || !Objects.equals(link, that.link) ||
                !Objects.equals(title, that.title) || !Objects.equals(departments, that.departments)) {
            return false;
        }
        return Objects.equals(faculty, that.faculty);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (link != null ? link.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + departments.hashCode();
        result = 31 * result + faculty.hashCode();
        return result;
    }
}
