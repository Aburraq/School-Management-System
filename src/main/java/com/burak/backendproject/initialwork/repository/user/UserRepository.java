package com.burak.backendproject.initialwork.repository.user;

import com.burak.backendproject.initialwork.entity.concretes.user.User;
import com.burak.backendproject.initialwork.entity.enums.RoleType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUsername(String username);
    boolean existsBySsn(String ssn);
    boolean existsByPhoneNumber(String phoneNumber);
    boolean existsByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.userRole.roleName = :roleName")
    Page<User> findByUserByRole(@Param("roleName") String roleName, Pageable pageable);
    List<User> findUserByNameContainingIgnoreCase(String userName);

    User findByUsername(String userName);

    List<User> findByAdvisorTeacherId(Long id);

    @Query("SELECT u FROM User u WHERE u.isAdvisor IS true")
    List<User> findAllAdvisorTeacher();

    @Query("SELECT (count (u) > 0) FROM User u WHERE u.userRole.roleType = ?1")
    boolean findStudent(RoleType roleType);

    @Query("SELECT MAX (u.studentNumber) FROM User u")
    int getMaxStudentNumber();

    @Query("SELECT u FROM User u WHERE u.id IN :idList")
    List<User> findByIdList(List<Long> idList);

}
