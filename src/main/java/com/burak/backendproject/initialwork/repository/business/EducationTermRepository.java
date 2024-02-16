package com.burak.backendproject.initialwork.repository.business;

import com.burak.backendproject.initialwork.entity.concretes.business.EducationTerm;
import com.burak.backendproject.initialwork.entity.enums.Term;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EducationTermRepository extends JpaRepository<EducationTerm, Long> {

    @Query("SELECT (count (e) > 0) FROM EducationTerm e WHERE e.term =?1 AND extract(YEAR FROM e.startDate) =?2")
    boolean existsByTermAndYear(Term term, int year);

    @Query("SELECT e FROM EducationTerm e WHERE extract(YEAR FROM e.startDate) =?1")
    List<EducationTerm> findByYear(int year);

}
