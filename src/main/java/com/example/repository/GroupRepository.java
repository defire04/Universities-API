package com.example.repository;

import com.example.models.Course;
import com.example.models.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository<Group, Integer> {

    Optional<Group> findGroupByCourseAndValueOnSite(Course course, String valueOnSite);
}
