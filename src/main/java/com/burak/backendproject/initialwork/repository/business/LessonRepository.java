package com.burak.backendproject.initialwork.repository.business;

import com.burak.backendproject.initialwork.entity.concretes.business.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {

    Optional<Lesson> getByLessonNameEqualsIgnoreCase(String lessonName);

}
