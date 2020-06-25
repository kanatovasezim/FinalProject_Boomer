package it.academy.FinalProject.Repository;

import it.academy.FinalProject.Entity.Course;
import it.academy.FinalProject.Entity.CourseUserStatus;
import it.academy.FinalProject.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CourseUserStatusRepo extends JpaRepository<CourseUserStatus, Long> {
    @Query( value = "select c.course_id from course_status c where c.user_id = :id and c.status = :status", nativeQuery=true)
    List<Long> findAllByCourseStatusAndUser(@Param("status") String status,@Param("id") Long userId);
    @Query( value = "select * from course_status c where c.user_id = :id and c.course_id = :courseID", nativeQuery=true)
    CourseUserStatus findByCourseAndUser(@Param("id") Long userId, @Param("courseID") Long courseId);
    @Query( value = "select * from course_status c where c.course_id = :id and c.status = :status", nativeQuery=true)
    List<CourseUserStatus> findAllByCourseStatusAndCourse(@Param("status") String status,@Param("id") Long courseId);
}
