package it.academy.FinalProject.Service;
import it.academy.FinalProject.Model.ApprovalUser;
import java.util.List;

public interface ApprovalService {
    void approveCourseByOwner( Long courseId, String client);
    void approveCourseByClient(String client, Long courseId);
    List<ApprovalUser> findApprovalsByClient(String login);
}
