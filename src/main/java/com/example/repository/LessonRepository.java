package com.example.repository;


import com.example.models.Lesson;
import com.example.models.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Integer> {


    Optional<Lesson> findBySubjectAndDate(Subject subject, String date);

    Optional<Lesson> findByDate(String date);

    long deleteByCreatedAtBefore(Date date);
}
