package it.academy.FinalProject.Controller;

import it.academy.FinalProject.Entity.User;
import it.academy.FinalProject.Service.CourseService;
import it.academy.FinalProject.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;



@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;
    @Autowired
    private CourseService courseService;

    @GetMapping("/all")
    public String getAllUsers(Model model) {
        model.addAttribute("allUsers", userService.getAll());
        return "User/userList";
    }
    @GetMapping("/profilePage")
    public String userIndex() {
        return "User/profilePage";
    }

    @GetMapping("/{login}")
    public User getByLogin(@PathVariable("login") String login) {
        return userService.findByLogin(login);
    }

    @PostMapping("/delete/{login}")
    public String delete(@PathVariable("login") String login) {
        userService.deleteByLogin(login);
        return "User/userList";
    }

    @PostMapping("{login}")
    public String saveUser(@PathVariable("login") String login, @RequestBody User u){
        if (userService.getAll().contains(userService.findByLogin(login))){
            User user = userService.findByLogin(login);
            String encodedPassword  = passwordEncoder.encode(u.getPassword());
            user.setId(u.getId());
            user.setPassword(encodedPassword);
            user.setLogin(u.getLogin());
            user.setIsActive(u.getIsActive());
            user.setCreatedDate(u.getCreatedDate());
            user.setBalance(u.getBalance());
            user.setEmail(u.getEmail());
            userService.save(user);
        }
        return "redirect:/User/userList";
//        else {
//
//        }
    }

    @PostMapping("/{login}/offerCourse/{id}")
    public void offerCourse(@PathVariable("id") Long courseId, @PathVariable("login") String login) {
        userService.offerCourse(courseService.getById(courseId));
    }

    @PostMapping("/{login}/approveRequest/{id}/{client}")
    public void approveRequest(@PathVariable("id") Long courseId, @PathVariable("login") String login, @PathVariable("client") String client) {
        userService.approveRequest(courseService.getById(courseId), login, client);
    }

    @PostMapping("/{login}/sendRequest/{id}")
    public void sendRequest(@PathVariable("id") Long courseId, @PathVariable("login") String login) {
        userService.sendRequest(courseService.getById(courseId), login);
    }
}
