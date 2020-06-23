package it.academy.FinalProject.Controller;

import it.academy.FinalProject.Service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/welcome")
    public String root(Model model) {
        model.addAttribute("epml", employeeService.getAll());
        return "index";
    }

}