package it.academy.FinalProject.Repository;
import it.academy.FinalProject.Entity.Role;
import it.academy.FinalProject.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    @Query( value = "select * from course_got c where c.s_user_id = :id", nativeQuery=true)
    Long findCourses(@Param("id") Long id);
    User findByLogin(String login);
    @Query( value = "select * from s_user u where u.email = :email", nativeQuery=true)
    User findByEmail(@Param("email") String email);
    @Query( value = "select COUNT(*) from s_user u where u.gender = :gender", nativeQuery=true)
    Integer findByGender(@Param("gender") String gender);
    @Query( value = "select * from s_user u where u.role_id = :role", nativeQuery=true)
    List<User> findAllByRole(@Param("role") Long roleId);
    List<User> findAllByCreatedDateLessThanEqualAndCreatedDateGreaterThanEqual( Date toDate, Date fromDate);
}
