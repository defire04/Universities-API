package com.example.services;


import com.example.models.Teacher;
import com.example.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class TeacherService {
    private final TeacherRepository teacherRepository;

    @Autowired
    public TeacherService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    @Transactional
    public Teacher save(Teacher teacher) {
        teacherRepository.save(teacher);
        return teacher;
    }

    public Optional<Teacher> findByFullName(String fullName){
        return teacherRepository.findByFullName(fullName);
    }
}
