package com.example.services;

import com.example.models.University;
import com.example.repository.UniversityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UniversityService {
    private final UniversityRepository universityRepository;

    @Autowired
    public UniversityService(UniversityRepository universityRepository) {
        this.universityRepository = universityRepository;
    }


    public void save(University university) {

        if (university.getLink() != null) {
            universityRepository.save(university);
        }
    }

    public boolean existsByLink(String link) {
        return universityRepository.existsByLink(link);
    }


    public University getUniversityByLink(String link) {
        return universityRepository.findByLink(link).orElse(new University(link));


    }

    public void update(University university) {
       save(university);
    }
}
