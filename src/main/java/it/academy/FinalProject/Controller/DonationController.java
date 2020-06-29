package it.academy.FinalProject.Controller;

import it.academy.FinalProject.Entity.Donation;
import it.academy.FinalProject.Service.DonationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/donation")
public class DonationController {

    @Autowired
    private DonationService donationService;

    @ModelAttribute("donation")
    public Donation newDonation() {
        return new Donation();
    }

    @GetMapping
    public String showRegistrationForm() {
        return "Registration/registerDonation";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("donation") @Valid Donation d) {
        System.out.println(d);
        donationService.save(d);
        return "redirect:/welcome";
    }
}
