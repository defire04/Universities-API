package com.example.services;


import com.example.models.Faculty;
import com.example.models.University;
import com.example.repository.FacultyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class FacultyService {
    private final FacultyRepository facultyRepository;

    @Autowired
    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    @Transactional
    public Faculty save(Faculty faculty) {
        facultyRepository.save(faculty);
        return faculty;
    }

    public Optional<Faculty> getByTitleAndUniversity(String title, University university) {
        return facultyRepository.findByTitleAndUniversity(title, university);
    }

}
