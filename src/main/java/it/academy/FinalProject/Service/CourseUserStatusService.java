package it.academy.FinalProject.Service;


import it.academy.FinalProject.Entity.CourseUserStatus;

import java.util.List;

public interface CourseUserStatusService {
    CourseUserStatus save(CourseUserStatus courseUserStatus);
    List<CourseUserStatus> getAll();
    void delete(Long id);
}
