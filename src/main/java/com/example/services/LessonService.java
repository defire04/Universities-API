package com.example.services;

import com.example.models.Lesson;
import com.example.repository.LessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LessonService {

    private final LessonRepository lessonRepository;

    @Autowired
    public LessonService(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }

    @Transactional
    public void save(Lesson lesson) {
        lessonRepository.save(lesson);
    }
}
