package it.academy.FinalProject.Service;

import it.academy.FinalProject.Entity.Approval;

public interface ApprovalService {
    void approveCourseByOwner(String owner, Long courseId, String client);
    void approveCourseByClient(String client, Long courseId, String owner);
    Approval findApprovalByClientAndCourse( String client,  Long id);
}
