package it.academy.FinalProject.Repository;

import it.academy.FinalProject.Entity.Course;
import it.academy.FinalProject.Entity.CourseUserStatus;
import it.academy.FinalProject.Entity.User;
import it.academy.FinalProject.Enum.CourseStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseUserStatusRepo extends JpaRepository<CourseUserStatus, Long> {
    CourseUserStatus findByCourseAndUser(Course course, User user);
    @Query( value = "select * from course_status c where c.user_id = :id and c.status = :status", nativeQuery=true)
    List<CourseUserStatus> findAllByCourseStatusAndUser(@Param("status") String status,@Param("id") Long userId);
//    List<CourseUserStatus> findAllByCourseStatusAndUser(CourseStatus status, User user);
}
