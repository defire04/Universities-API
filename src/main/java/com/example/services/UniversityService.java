package com.example.services;

import com.example.models.University;
import com.example.repository.UniversityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class UniversityService {
    private final UniversityRepository universityRepository;

    @Autowired
    public UniversityService(UniversityRepository universityRepository) {
        this.universityRepository = universityRepository;
    }

    public University save(University university) {
        university.setLink(prepareUniversityLink(university.getLink()));

        University uniResult = findByLink(university.getLink()).orElseGet(() ->
                universityRepository.save(new University(university.getLink(), university.getTitle())));

        uniResult.setLastUpdate(LocalDate.now());
        return uniResult;
    }


    public void update(University university) {
        universityRepository.save(university);
    }

    public Optional<University> findByLink(String link) {
        return universityRepository.findByLink(prepareUniversityLink(link));
    }

    public Optional<University> findById(Integer id) {
        return universityRepository.findById(id);
    }

    public List<University> findAll() {
        return universityRepository.findAll();
    }


    public String prepareUniversityLink(String link) {
        String linkEndOn = ".edu.ua";
        int endIndex = link.indexOf(linkEndOn);

        if(endIndex == -1){
            throw new RuntimeException();
        }

        return link.substring(0, endIndex + linkEndOn.length());
    }
}
