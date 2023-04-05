package com.example.services;

import com.example.models.University;
import com.example.repository.UniversityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service

public class UniversityService {
    private final UniversityRepository universityRepository;

    @Autowired
    public UniversityService(UniversityRepository universityRepository) {
        this.universityRepository = universityRepository;
    }


    public University save(University university) {
        University uniResult = findByLink(university.getLink()).orElseGet(() ->
                universityRepository.save(new University(university.getLink(), university.getTitle())));

        uniResult.setLastUpdate(LocalDate.now());
        return uniResult;
    }


    public void update(University university) {
        universityRepository.save(university);
    }

    public Optional<University> findByLink(String link) {
        return universityRepository.findByLink(link);
    }

    public List<University> findAll() {
        System.out.println("1111");
        return universityRepository.findAll();
    }
}
