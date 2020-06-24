package it.academy.FinalProject.Service.ServiceImpl;

import it.academy.FinalProject.Entity.Course;
import it.academy.FinalProject.Entity.CourseUserStatus;
import it.academy.FinalProject.Entity.User;
import it.academy.FinalProject.Enum.CourseStatus;
import it.academy.FinalProject.Model.CourseUsers;
import it.academy.FinalProject.Model.RegisterCourse;
import it.academy.FinalProject.Model.RegisterUser;
import it.academy.FinalProject.Repository.CourseRepo;
import it.academy.FinalProject.Repository.CourseUserStatusRepo;
import it.academy.FinalProject.Repository.UserRepo;
import it.academy.FinalProject.Service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseRepo courseRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private CourseUserStatusRepo statusRepo;

    @Override
    public List<Course> getAll() {
        return courseRepo.findAll();
    }

    @Override
    public List<Course> getTakingCourses(String login) {
        return userRepo.findByLogin(login).getCourseGet();
    }

    @Override
    public List<Course> getRequestedCourses(String login) {
        List<Long> list = statusRepo.findAllByCourseStatusAndUser("REQUESTED", userRepo.findByLogin(login).getId());
        List<Course> courseList = new ArrayList<>();
        for (Long l : list) {
            Course c = courseRepo.findById(l).get();
            courseList.add(c);
        }
        return courseList;
    }

    @Override
    public List<CourseUsers> getRequestingUsers(List<Course> courses) {
        List<Long> ids = courses.stream().map(Course::getId).collect(Collectors.toList());
        List<CourseUsers> courseUsersList = new ArrayList<>();
        List<User> usersList = new ArrayList<>();
        for (Long u : ids) {
            List<Long> list = statusRepo.findAllByCourseStatusAndUser("REQUESTED", u);
            for (Long l : list) {
                usersList.add(userRepo.findById(l).get());
            }
            for (Course l : courses) {
                CourseUsers cu = CourseUsers.builder()
                        .course(l)
                        .users(usersList)
                        .build();
                courseUsersList.add(cu);
            }
        }
        return courseUsersList;
    }

    @Override
    public List<Course> getOfferingCourses(String login) {
        return courseRepo.findOfferingCourses(userRepo.findByLogin(login).getId());
    }

    @Override
    public List<Course> getCompletedCourses(String login) {
        List<Long> list = statusRepo.findAllByCourseStatusAndUser("COMPLETED", userRepo.findByLogin(login).getId());
        List<Course> courseList = new ArrayList<>();
        for (Long l : list) {
            Course c = courseRepo.findById(l).get();
            courseList.add(c);
        }
        return courseList;
    }

    @Override
    public Course save(Course course) {
        return courseRepo.save(course);
    }

    @Override
    public Course getById(Long id) {
        return courseRepo.findById(id).orElse(new Course());
    }

    @Override
    public void delete(Long id) {
        courseRepo.deleteById(id);
    }

    @Override
    public Course findByName(String name) {
        return courseRepo.findByName(name);
    }

    @Override
    public void deleteByName(String name) {
        if (courseRepo.existsById(courseRepo.findByName(name).getId())) {
            courseRepo.deleteById(courseRepo.findByName(name).getId());
        }
    }

    @Override
    public Course saveModel(RegisterCourse c) {
        Course course = Course.builder()
                .id(c.getId())
                .name(c.getName())
                .author(c.getAuthor())
                .languageList(c.getLanguageList())
                .duration(c.getDuration())
                .cost(c.getCost())
                .description(c.getDescription())
                .freePlaces(c.getFreePlaces())
                .category(c.getCategory())
                .requests(c.getRequests())
                .build();
        courseRepo.save(course);
        return course;
    }
}
