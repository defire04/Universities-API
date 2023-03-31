package com.example.dto;

import java.time.LocalDate;

public class LessonItem {
    private String teacher;
    private String subject;
    private String faculty;
    private String course;

    private LocalDate lessonDate;

    private String pairNumber;


    public LessonItem() {
    }

    public LessonItem(String teacher, String subject, String faculty, String course) {
        this.teacher = teacher;
        this.subject = subject;
        this.faculty = faculty;
        this.course = course;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public LocalDate getLessonDate() {
        return lessonDate;
    }

    public void setLessonDate(LocalDate lessonDate) {
        this.lessonDate = lessonDate;
    }

    public String getPairNumber() {
        return pairNumber + " пара";
    }

    public void setPairNumber(String pairNumber) {
        this.pairNumber = pairNumber;
    }
}

