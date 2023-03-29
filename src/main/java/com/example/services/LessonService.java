package com.example.services;

import com.example.models.Lesson;
import com.example.models.Subject;
import com.example.repository.LessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class LessonService {

    private final LessonRepository lessonRepository;

    @Autowired
    public LessonService(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }

    @Transactional
    public Lesson save(Lesson lesson) throws Exception {
        if(lesson.getFaculty() == null || lesson.getCourse() == null ){
            throw new RuntimeException();
        }
        lessonRepository.save(lesson);
        return lesson;
    }

    public Optional<Lesson> findBySubjectAndDate(Subject subject, String date) {
        return lessonRepository.findBySubjectAndDate(subject, date);
    }

    public List<Lesson> getAllLessons(){
        return lessonRepository.findAll();
    }
}
