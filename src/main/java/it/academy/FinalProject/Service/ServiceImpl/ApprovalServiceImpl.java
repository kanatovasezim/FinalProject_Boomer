package it.academy.FinalProject.Service.ServiceImpl;

import it.academy.FinalProject.Entity.Approval;
import it.academy.FinalProject.Repository.ApprovalRepo;
import it.academy.FinalProject.Service.ApprovalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApprovalServiceImpl implements ApprovalService {
    @Autowired
    private ApprovalRepo approvalRepo;
    @Override
    public void approveCourseByOwner(String owner, Long courseId, String client) {
        Approval a = findApprovalByClientAndCourse(client,courseId);
        a.setOwnerApproval(true);
        approvalRepo.save(a);
    }

    @Override
    public Approval findApprovalByClientAndCourse(String client, Long id) {
        return approvalRepo.findApprovalByClientAndCourse(client,id);
    }

    @Override
    public void approveCourseByClient(String client, Long courseId, String owner) {
        Approval a = findApprovalByClientAndCourse(client,courseId);
        a.setClientApproval(true);
        approvalRepo.save(a);
    }
}
