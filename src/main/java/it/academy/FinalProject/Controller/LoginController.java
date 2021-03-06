package it.academy.FinalProject.Controller;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping
public class LoginController {
    @GetMapping
    @RequestMapping("/login")
    public String loginUser(){
        return "Registration/login";
    }

    @GetMapping
    @RequestMapping("/redirect")
    public String redirect(HttpServletRequest httpServletRequest, Model model){
        if(httpServletRequest.isUserInRole("ADMIN")) {
            return "redirect:/admin";
        } else if(httpServletRequest.isUserInRole("USER")) {
            return "redirect:/user/profile";
        } else if(httpServletRequest.isUserInRole("PUBLISHER")){
            return "redirect:/post/profile";
        } else {
            return "Registration/login";
        }
    }

    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout";
    }
}
