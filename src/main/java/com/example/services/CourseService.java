package com.example.services;

import com.example.models.Course;
import com.example.models.Faculty;
import com.example.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class CourseService {
    private final CourseRepository courseRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Transactional
    public Course save(Course course) {
        courseRepository.save(course);

        return course;
    }

    public Optional<Course> findCourseByFacultyAndNumber(Faculty faculty, String number) {
        return courseRepository.findCourseByFacultyAndNumber(faculty, number);
    }

    public Course findByFacultyValueOnSite(Faculty faculty, String valueOnSite) {
        return courseRepository.findByFacultyAndValueOnSite(faculty, valueOnSite).orElse(null);
    }

}
