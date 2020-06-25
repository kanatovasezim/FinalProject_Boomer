package it.academy.FinalProject.Service.ServiceImpl;

import it.academy.FinalProject.Entity.Approval;
import it.academy.FinalProject.Repository.ApprovalRepo;
import it.academy.FinalProject.Service.ApprovalService;
import it.academy.FinalProject.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApprovalServiceImpl implements ApprovalService {
    @Autowired
    private ApprovalRepo approvalRepo;
    @Autowired
    private UserService userService;
    @Override
    public void approveCourseByOwner(Long courseId, String client) {
        Approval a = approvalRepo.findApprovalByClientAndCourse( userService.findByLogin(client).getId(),courseId);
        a.setOwnerApproval(true);
        approvalRepo.save(a);
    }


    @Override
    public void approveCourseByClient(String client, Long courseId) {
        Approval a = approvalRepo.findApprovalByClientAndCourse(userService.findByLogin(client).getId(),courseId);
        a.setClientApproval(true);
        approvalRepo.save(a);
    }
}
