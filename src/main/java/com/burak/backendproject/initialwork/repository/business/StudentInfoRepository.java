package com.burak.backendproject.initialwork.repository.business;

import com.burak.backendproject.initialwork.entity.concretes.business.StudentInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentInfoRepository extends JpaRepository<StudentInfo, Long> {

    @Query("SELECT (count (s)>0) FROM StudentInfo s WHERE s.student.id = ?1 AND s.lesson.lessonName = ?2")
    boolean giveMeDuplications(Long studentId, String lessonName);

    @Query("SELECT s FROM StudentInfo s WHERE s.student.id = ?1")
    List<StudentInfo> findByStudentId(Long studentId);

    @Query("SELECT s FROM StudentInfo s WHERE s.teacher.username=?1")
    Page<StudentInfo> findByTeacherUsername(String username, Pageable pageable);

    @Query("SELECT s FROM StudentInfo s WHERE s.student.username=?1")
    Page<StudentInfo> findByStudentUsername(String username, Pageable pageable);

}
