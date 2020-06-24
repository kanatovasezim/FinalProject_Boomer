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
    List<Course> findCoursesByCourseStatus(CourseStatus courseStatus);
}
