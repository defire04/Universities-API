package com.example.repository;


import com.example.models.Course;

import com.example.models.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository  extends JpaRepository<Course, Integer> {
    List<Course> getCourseByIdAndFaculty(Integer id, Faculty faculty);
}
