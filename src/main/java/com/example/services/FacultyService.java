package com.example.services;


import com.example.models.Faculty;
import com.example.models.University;
import com.example.repository.FacultyRepository;
import org.hibernate.annotations.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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

    public Optional<Faculty> findByTitleAndUniversity(String title, University university) {
        return facultyRepository.findByTitleAndUniversity(title, university);
    }

    public Optional<Faculty> findByValueOnSite(String valueOnSite)  {
        return facultyRepository.findByValueOnSite(valueOnSite);
    }

    public List<Faculty> findByUniversity(University university){
        return facultyRepository.findByUniversity(university);
    }
}
