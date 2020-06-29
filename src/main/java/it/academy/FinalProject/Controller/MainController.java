package it.academy.FinalProject.Controller;

import it.academy.FinalProject.Service.EmployeeService;
import it.academy.FinalProject.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private UserService userService;

    @GetMapping("/welcome")
    public String root(Model model) {
        model.addAttribute("epml", employeeService.getAll());
        return "index";
    }


    @GetMapping("/admin")
    public String admin(Model model,  Authentication authentication) {
        model.addAttribute("employee", employeeService.getAll());
        model.addAttribute("admin", userService.findByLogin(authentication.getName()));
        model.addAttribute("userCount", userService.getLoggedInUsers());
        model.addAttribute("employeeCount", userService.getLoggedInEmployees());
        model.addAttribute("F", userService.getFemaleUserCount());
        model.addAttribute("M", userService.getMaleUserCount());
        return "Admin/adminProfilePage";
    }

    @GetMapping("/statistics")
    public String statistics(Model model) {
        model.addAttribute("statistics", employeeService.getAll());
        return "Statistics/overall";
    }

}