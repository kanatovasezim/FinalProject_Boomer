package it.academy.FinalProject.Controller;

import it.academy.FinalProject.Service.ApprovalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("approve")
public class ApproveController {
    @Autowired
    ApprovalService approvalService;

    @PostMapping("/ownerApproveCourse/{id}/{client}")
    public String approveCourseByOwner(@PathVariable("id") Long id, @PathVariable("client") String client) {
        approvalService.approveCourseByOwner( id, client);
        return "redirect:/user/profile";
    }

    @PostMapping("/{login}/clientApproveCourse/{id}")
    public String approveCourseByClient(@PathVariable("login") String client, @PathVariable("id") Long id) {
        approvalService.approveCourseByClient(client, id);
        return "redirect:/user/profile";
    }
}
