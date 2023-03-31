package com.example.services;

import com.example.models.University;
import com.example.repository.UniversityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UniversityService {
    private final UniversityRepository universityRepository;

    @Autowired
    public UniversityService(UniversityRepository universityRepository) {
        this.universityRepository = universityRepository;
    }

    @Transactional
    public University save(University university) {
        if (university.getLink() == null) {
            throw new NullPointerException();
        }

        universityRepository.save(university);
        return university;
    }


    public void update(University university) {
        save(university);
    }

    public Optional<University> findByLink(String link) {
        return universityRepository.findByLink(link);
    }

    public List<University> findAll(){
        System.out.println("1111");
        return universityRepository.findAll();
    }
}
