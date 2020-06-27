package it.academy.FinalProject.Controller;

import it.academy.FinalProject.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/statistic")
public class StatisticsController {
    @Autowired
    private UserService userService;
    @GetMapping("/allLoggedUsers")
    public Integer getLoggedUsers(){
        return userService.getLoggedInUsers() - userService.getLoggedInEmployees();
    }
    @GetMapping("/allLoggedEmployees")
    public Integer getLoggedEmployees(){
        return userService.getLoggedInEmployees();
    }

    @GetMapping("/femaleCount")
    public Integer getFemaleCount(){
        return userService.getFemaleUserCount();
    }
    @GetMapping("/maleCount")
    public Integer getMaleCount(){
        return userService.getMaleUserCount();
    }
    @GetMapping
    public String getAllUsers() {
        return "SummaryData/databank";
    }
    @GetMapping("/mailSender")
    public String sendMails() {
        return "MailSender/mailSenderPage";
    }
}
