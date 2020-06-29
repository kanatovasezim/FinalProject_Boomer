package it.academy.FinalProject.Controller;

import it.academy.FinalProject.Service.DonationService;
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
    @Autowired
    private DonationService donationService;

    @GetMapping("/welcome")
    public String root(Model model) {
        model.addAttribute("epml", employeeService.getAll());
        return "index";
    }


    @GetMapping("/admin")
    public String admin(Model model,  Authentication authentication) {
        model.addAttribute("employee", employeeService.getAll());
        model.addAttribute("totalEmployeeCount", employeeService.getAll().size());
        model.addAttribute("admin", userService.findByLogin(authentication.getName()));
        model.addAttribute("userCount", userService.getLoggedInUsers());
        model.addAttribute("employeeCount", userService.getLoggedInEmployees());
        model.addAttribute("dayOfWeek", userService.getDayOfWeek());
        model.addAttribute("userCountDOW", userService.getUserCountByDOW());
        model.addAttribute("monthsList", donationService.getAllMonth());
        model.addAttribute("donationCount", donationService.getDonationCountByMonth());
        model.addAttribute("totalDonationCount", donationService.getDonationTotalCount());
        model.addAttribute("totalUserCount", userService.findAllByRole("User").size());
        model.addAttribute("totalUserCountWeek", userService.getUserTotalCountByWeek());
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