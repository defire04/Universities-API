package com.example.repository;


import com.example.models.Faculty;
import com.example.models.University;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FacultyRepository extends JpaRepository<Faculty, Integer> {

    Optional<Faculty> findByTitleAndUniversity(String title, University university);


}
