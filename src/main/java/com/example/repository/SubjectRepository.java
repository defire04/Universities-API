package com.example.repository;

import com.example.models.Subject;
import com.example.models.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubjectRepository  extends JpaRepository<Subject, Integer> {

   Optional<Subject> findByTitleAndTeachersContaining(String title, Teacher teacher);
}
