package com.example.services;

import com.example.models.Course;
import com.example.models.Faculty;
import com.example.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CourseService {
    private final CourseRepository courseRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }


    public Course save(Course course) {
        courseRepository.save(course);

        return course;
    }


    public List<Course> save(List<Course> courses) {
        List<Course> result = new ArrayList<>();
        courses.forEach(course -> {
            result.add(findCourseByFacultyAndNumber(course.getFaculty(), course.getNumber())
                    .orElseGet(() -> courseRepository.save(new Course(course.getNumber(), course.getFaculty(), course.getValueOnSite()))));
        });

        return result;
    }

    public Optional<Course> findCourseByFacultyAndNumber(Faculty faculty, String number) {
        return courseRepository.findCourseByFacultyAndNumber(faculty, number);
    }

    public Optional<Course> findByFacultyAndValueOnSite(Faculty faculty, String valueOnSite) {
        return courseRepository.findByFacultyAndValueOnSite(faculty, valueOnSite);
    }

    public List<Course> findByFaculty(Faculty faculty) {
        return courseRepository.findByFaculty(faculty);
    }


}
