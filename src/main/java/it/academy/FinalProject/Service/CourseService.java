package it.academy.FinalProject.Service;

import it.academy.FinalProject.Entity.Course;
import it.academy.FinalProject.Entity.User;
import it.academy.FinalProject.Enum.CourseStatus;
import it.academy.FinalProject.Model.CourseUsers;
import it.academy.FinalProject.Model.RegisterCourse;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CourseService {
    List<Course> getAll();
    Course getById(Long id);
    Course save(Course Course);
    void delete(Long id);
    Course findByName(String name);
    void deleteByName(String name);
    Course saveModel(RegisterCourse c);
    List<Course> getTakingCourses(String login);
    List<Course> getRequestedCourses(String login);
    List<CourseUsers> getRequestingUsers(List<Course> courses);
    List<CourseUsers> getOfferingCourses(String login);
    List<Course> getCompletedCourses(String login);
}
