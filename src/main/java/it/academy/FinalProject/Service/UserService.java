package it.academy.FinalProject.Service;

import it.academy.FinalProject.Entity.Course;
import it.academy.FinalProject.Entity.Employee;
import it.academy.FinalProject.Entity.Role;
import it.academy.FinalProject.Entity.User;
import it.academy.FinalProject.Model.LoginUser;
import it.academy.FinalProject.Model.RegisterEmpl;
import it.academy.FinalProject.Model.RegisterUser;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    List<User> getAll();
    User getById(Long id);
    User save(User user);
    User saveModelUser(RegisterUser user);
    void delete(Long id);
    User findByLogin(String login);
    Long findCourses(Long id);
    void deleteByLogin(String login);
    void sendRequest(Course course, String login);
    void removeRequest(Course course, String login);
    void offerCourse(Course course);
    void finishCourse(Course course, String login);
    void approveRequest(Course course, String owner, String client);
    User findByEmail(String login);
    Boolean checkIfComplete(Long courseId, Long userId);
    Integer getLoggedInUsers();
    Integer getLoggedInEmployees();
    Integer getFemaleUserCount();
    Integer getMaleUserCount();
}
