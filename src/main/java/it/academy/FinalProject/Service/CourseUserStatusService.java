package it.academy.FinalProject.Service;


import it.academy.FinalProject.Entity.Course;
import it.academy.FinalProject.Entity.CourseUserStatus;
import it.academy.FinalProject.Entity.User;

import java.util.List;

public interface CourseUserStatusService {
    CourseUserStatus save(CourseUserStatus courseUserStatus);
    List<CourseUserStatus> getAll();
    void delete(Long id);
    CourseUserStatus findCourseUserStatusByCourseAndUser(Course course, User user);
}
