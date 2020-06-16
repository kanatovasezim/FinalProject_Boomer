package it.academy.FinalProject.Controller;

import it.academy.FinalProject.Entity.Course;
import it.academy.FinalProject.Entity.User;
import it.academy.FinalProject.Repository.CourseRepo;
import it.academy.FinalProject.Service.CourseService;
import it.academy.FinalProject.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private CourseService courseService;

    @GetMapping("/all")
    public List<User> getAllUsers(){
        return userService.getAll();
    }
    @GetMapping("/{login}")
    public User getByLogin(@PathVariable("login") String login){
        return userService.findByLogin(login);
    }
    @DeleteMapping("/{login}")
    public void delete(@PathVariable("login") String login){
        userService.deleteByLogin(login);
    }
    @PostMapping("/{login}/offerCourse/{id}")
    public void offerCourse(@PathVariable("id") Long courseId, @PathVariable("login") String login){
        userService.offerCourse(courseService.getById(courseId));
    }
    @PostMapping("/{login}/approveRequest/{id}/{client}")
    public void approveRequest(@PathVariable("id") Long courseId, @PathVariable("login") String login, @PathVariable("client") String client){
        userService.approveRequest(courseService.getById(courseId), login, client);
    }

    @PostMapping("/{login}/sendRequest/{id}")
    public void sendRequest(@PathVariable("id") Long courseId, @PathVariable("login") String login){
        userService.sendRequest(courseService.getById(courseId), login);
    }
}
