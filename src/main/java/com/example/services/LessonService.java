package com.example.services;

import com.example.models.Lesson;
import com.example.models.Subject;
import com.example.repository.LessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class LessonService {

    private final LessonRepository lessonRepository;

    @Autowired
    public LessonService(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }

    @Transactional
    public Lesson save(Lesson lesson) {
        lessonRepository.save(lesson);
        return lesson;
    }

    public Optional<Lesson> findBySubjectAndDateAndPairNumber(Subject subject, LocalDate date, String pairNumber) {
        return lessonRepository.findBySubjectAndDateAndPairNumber(subject, date, pairNumber);
    }


//    public Optional<Lesson> findByDate(String date){
//        return lessonRepository.findByDate(date);
//    }

    public List<Lesson> getAllLessons(){
        return lessonRepository.findAll();
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void deleteOldData() {
//        LocalDate tenDaysAgo = LocalDate.now().minusDays(10);
//        long rowsDeleted = lessonRepository.deleteByCreatedAtBefore(tenDaysAgo);
//        System.out.println("Rows deleted: " + rowsDeleted);
    }

//    public void deleteOldData() {
//        LocalDate tenDaysAgo = LocalDate.now().minusDays(10);
//        long rowsDeleted = lessonRepository.deleteAllByDateBefore(tenDaysAgo);
//        System.out.println("Rows deleted: " + rowsDeleted);
//    }
}
