package com.example.services.mappers;

import com.example.dto.CourseDTO;
import com.example.models.Course;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseMapperService {

    private final ModelMapper modelMapper;

    @Autowired
    public CourseMapperService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public CourseDTO convertCourseToCourseDTO(Course course) {
        return modelMapper.map(course, CourseDTO.class);
    }

    public List<CourseDTO> convertCourseListToCourseDTOList(List<Course> courses) {
        return courses.stream().map(this::convertCourseToCourseDTO).collect(Collectors.toList());
    }
}
