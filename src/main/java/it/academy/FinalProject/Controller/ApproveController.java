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
    @PostMapping("/{login}/ownerApproveCourse/{id}/{client}")
    public void approveCourseByOwner(@PathVariable("login") String login, @PathVariable("id")Long id, @PathVariable("client") String client){
            approvalService.approveCourseByOwner(login, id, client);
    }
    @PostMapping("/{login}/clientApproveCourse/{id}/{owner}")
    public void approveCourseByClient(@PathVariable("login") String client, @PathVariable("id") Long id, @PathVariable("owner") String owner){
        approvalService.approveCourseByClient(client, id, owner);
    }
}
