package it.academy.FinalProject.Service.ServiceImpl;

import it.academy.FinalProject.Entity.Approval;
import it.academy.FinalProject.Entity.Course;
import it.academy.FinalProject.Entity.CourseUserStatus;
import it.academy.FinalProject.Model.CourseUsers;
import it.academy.FinalProject.Model.RegisterCourse;
import it.academy.FinalProject.Repository.ApprovalRepo;
import it.academy.FinalProject.Repository.CourseRepo;
import it.academy.FinalProject.Repository.CourseUserStatusRepo;
import it.academy.FinalProject.Repository.UserRepo;
import it.academy.FinalProject.Service.CourseService;
import it.academy.FinalProject.Service.UserService;
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
    private UserService userService;
    @Autowired
    private CourseUserStatusRepo statusRepo;
    @Autowired
    private ApprovalRepo approvalRepo;

    @Override
    public List<Course> getAll() {
        return courseRepo.findAll();
    }

    @Override
    public List<Course> getAllOther(String name) {
        List<Course> owningCourses = findOfferingCourses(name);
        System.out.println(owningCourses);
        List<Course> courseList = courseRepo.findAll();
        System.out.println(courseList);
//        for (Course c : courseList) {
//            for (Course co : owningCourses) {
//                if (c.getId().equals(co.getId())){
//                    courseList.remove(c);
//                }
//            }
//        }
        return courseList;
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
    public List<Course> findOfferingCourses(String login) {
        return courseRepo.findOfferingCourses(userRepo.findByLogin(login).getId());
    }

    @Override
    public List<CourseUsers> getRequestingUsers(List<Course> courses) {
        List<Long> ids = courses.stream().map(Course::getId).collect(Collectors.toList());
        List<CourseUsers> courseUsersList = new ArrayList<>();
        for (Long u : ids) {
            List<CourseUserStatus> list = statusRepo.findAllByCourseStatusAndCourse("REQUESTED", u);
            for (CourseUserStatus l : list) {
                CourseUsers cu = CourseUsers.builder()
                        .course(l.getCourse())
                        .users(l.getUser())
                        .build();
                courseUsersList.add(cu);
            }
        }
        return courseUsersList;
    }

    @Override
    public List<CourseUsers> getOfferingCourses(String login) {
        List<Course> courseList = courseRepo.findOfferingCourses(userRepo.findByLogin(login).getId());
        List<CourseUsers> courseUsersList = new ArrayList<>();
        for (Course c : courseList) {
            List<Long> users = courseRepo.findUsersByCourse(c.getId());
            for (Long u : users) {
                CourseUsers cu = CourseUsers.builder()
                        .course(c)
                        .users(userService.getById(u))
                        .build();
                courseUsersList.add(cu);
            }
        }
        return courseUsersList;
    }

    @Override
    public List<Course> getCompletedCourses(String login) {
        List<Long> list = statusRepo.findAllByCourseStatusAndUser("COMPLETED", userRepo.findByLogin(login).getId());
        List<Course> courseList = new ArrayList<>();
        for (Long l : list) {
            Course c = courseRepo.findById(l).get();
            System.err.println(c);
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
        List<Approval> a = approvalRepo.findAllByCourse(getById(id));
        for (Approval ap: a ) {
            ap.getClient().getCourseGet().remove(getById(id));
            ap.getClient().getRequestedCourses().remove(getById(id));
            approvalRepo.delete(ap);
        }
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

//    @Override
//    public List<Course> findByCategory(RegisterCategory category) {
//        System.err.println("" + category);
//        List<Long> list = courseRepo.findAllByCategory("" + category);
//        System.out.println(list);
//        List<Course> courseList = new ArrayList<>();
//        for (Long l : list) {
//            courseList.add(getById(l));
//        }
//        return courseList;
//    }
}
