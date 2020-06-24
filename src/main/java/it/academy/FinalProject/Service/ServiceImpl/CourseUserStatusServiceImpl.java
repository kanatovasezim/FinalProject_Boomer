package it.academy.FinalProject.Service.ServiceImpl;

import it.academy.FinalProject.Entity.Course;
import it.academy.FinalProject.Entity.CourseUserStatus;
import it.academy.FinalProject.Entity.User;
import it.academy.FinalProject.Repository.CourseUserStatusRepo;
import it.academy.FinalProject.Service.CourseUserStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseUserStatusServiceImpl implements CourseUserStatusService {
    @Autowired
    private CourseUserStatusRepo repo;

    @Override
    public CourseUserStatus findCourseUserStatusByCourseAndUser(Course course, User user) {
        return repo.findByCourseAndUser(course,user);
    }

    @Override
    public CourseUserStatus save(CourseUserStatus courseUserStatus) {
        return repo.save(courseUserStatus);
    }

    @Override
    public List<CourseUserStatus> getAll() {
        return repo.findAll();
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }
}
