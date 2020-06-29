package it.academy.FinalProject.Controller;
import it.academy.FinalProject.Entity.Course;
import it.academy.FinalProject.Entity.User;
import it.academy.FinalProject.Model.RegisterUser;
import it.academy.FinalProject.Repository.UserRepo;
import it.academy.FinalProject.Service.ApprovalService;
import it.academy.FinalProject.Service.CourseService;
import it.academy.FinalProject.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;


@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private ApprovalService approvalService;

    @ModelAttribute("user")
    public RegisterUser newUser() {
        return new RegisterUser();
    }

    @GetMapping
    public String showRegistrationForm() {
        return "Registration/register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") @Valid RegisterUser u, BindingResult result) {
        u.setPassword(passwordEncoder.encode(u.getPassword()));
        if (userService.findByLogin(u.getLogin()) != null) {
            result.rejectValue("login", null, "Login already exists");
        }
        if (result.hasErrors()) {
            return "Registration/register";
        }
        userService.saveModelUser(u);
        return "redirect:/login";
    }

    @GetMapping("/all")
    public String getAllUsers(Model model, Authentication authentication) {
        model.addAttribute("allUsers", userService.findAllByRole("User"));
        model.addAttribute("admin", userService.findByLogin(authentication.getName()));
        return "User/user";
    }

    @GetMapping("/profile")
    public String showNotificationPage(Model model, Authentication authentication) {
        List<Course> courseList = courseService.findOfferingCourses(authentication.getName());
        model.addAttribute("user", userService.findByLogin(authentication.getName()));
        model.addAttribute("approval", approvalService.findApprovalsByClient(authentication.getName()));
        model.addAttribute("notif", courseService.getRequestingUsers(courseList));
        return "User/notifications";
    }
    @GetMapping("/allCourses")
    public String showAllCourses(Model model, Authentication authentication) {
        model.addAttribute("allCourses", courseService.getAll());
        model.addAttribute("user", userService.findByLogin(authentication.getName()));
        return "Course/allCourses";
    }

    @GetMapping("/{login}")
    public User getByLogin(@PathVariable("login") String login) {
        return userService.findByLogin(login);
    }

    @GetMapping("/delete/{login}")
    public String delete(@PathVariable("login") String login) {
        userService.deleteByLogin(login);
        return "redirect:/user/all";
    }

//    @PostMapping("/update/{login}")
//    public String updateUser(@PathVariable("login") String login, @RequestBody User u){
//        if (userService.getAll().contains(userService.findByLogin(login))){
//            User user = userService.findByLogin(login);
//            String encodedPassword  = passwordEncoder.encode(u.getPassword());
//            user.setId(u.getId());
//            user.setPassword(encodedPassword);
//            user.setLogin(u.getLogin());
//            user.setIsActive(u.getIsActive());
//            user.setCreatedDate(u.getCreatedDate());
//            user.setBalance(u.getBalance());
//            user.setEmail(u.getEmail());
//            userService.save(user);
//        }
//        return "redirect:/User/userList";
//    }

    @PostMapping("/{login}/offerCourse/{id}")
    public void offerCourse(@PathVariable("id") Long courseId, @PathVariable("login") String login) {
        userService.offerCourse(courseService.getById(courseId));
    }

    @PostMapping("/{login}/approveRequest/{id}/{client}")
    public String approveRequest(@PathVariable("id") Long courseId, @PathVariable("login") String login, @PathVariable("client") String client) {
        userService.approveRequest(courseService.getById(courseId), login, client);
        return "redirect:/user/profile";
    }

    @PostMapping("/{login}/sendRequest/{id}")
    public String sendRequest(@PathVariable("id") Long courseId, @PathVariable("login") String login) {
        userService.sendRequest(courseService.getById(courseId), login);
        return "redirect:/user/profile";
    }

    @PostMapping("/{login}/removeRequest/{id}")
    public String removeRequest(@PathVariable("id") Long courseId, @PathVariable("login") String login) {
        userService.removeRequest(courseService.getById(courseId), login);
        return "redirect:/user/profile";
    }

    @PostMapping("/{login}/finishCourse/{id}")
    public String finishCourse(@PathVariable("id") Long courseId, @PathVariable("login") String login) {
        userService.finishCourse(courseService.getById(courseId), login);
        return "redirect:/user/profile";
    }



}
