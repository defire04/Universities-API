package com.example.repository;

import com.example.models.University;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UniversityRepository extends JpaRepository<University, Integer> {

    Boolean existsByLink(String link);

    Optional<University> findByLink(String link);
}
