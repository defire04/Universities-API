package com.example.dto;

import com.example.models.Course;
import com.example.models.Faculty;
import com.example.models.Subject;
import com.example.models.Teacher;

public class Schedule {
    String teacher;
}

class ScheduleItem{
    private Teacher teacher;
    private Subject subject;
    private Faculty faculty;
    private Course course;

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
