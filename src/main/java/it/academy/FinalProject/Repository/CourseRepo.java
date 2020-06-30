package it.academy.FinalProject.Repository;

import it.academy.FinalProject.Entity.Course;
import it.academy.FinalProject.Enum.CourseStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepo extends JpaRepository<Course, Long> {
    @Query( value = "select * from course c where c.name = :name", nativeQuery=true)
    Course findByName(@Param("name") String name);
    @Query( value = "select * from course c where c.author = :id", nativeQuery=true)
    List<Course> findOfferingCourses(@Param("id") Long id);
    @Query( value = "select c.s_user_id from course_got c where c.course_id = :id", nativeQuery=true)
    List<Long> findUsersByCourse(@Param("id") Long id);
    @Query( value = "select c.course_id from category c where c.category = :category", nativeQuery=true)
    List<Long> findAllByCategory(@Param("category") String category);
}
