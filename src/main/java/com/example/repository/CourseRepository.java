package com.example.repository;


import com.example.models.Course;

import com.example.models.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository  extends JpaRepository<Course, Integer> {

    Optional<Course> findCourseByFacultyAndNumber(Faculty faculty, String number);

    Optional<Course> findByFacultyAndValueOnSite(Faculty faculty, String valueOnSite);
}
