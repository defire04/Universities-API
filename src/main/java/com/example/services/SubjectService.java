package com.example.services;


import com.example.models.Subject;
import com.example.models.Teacher;
import com.example.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class SubjectService {
    private final SubjectRepository subjectRepository;

    @Autowired
    public SubjectService(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    @Transactional
    public Subject save(Subject subject) {
        subjectRepository.save(subject);
        return subject;
    }

    public Optional<Subject> findByTitleAndTeachersContaining(String title, Teacher teacher){
        return subjectRepository.findByTitleAndTeachersContaining(title, teacher);
    }


}
