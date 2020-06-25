package it.academy.FinalProject.Service;

import it.academy.FinalProject.Entity.Approval;

public interface ApprovalService {
    void approveCourseByOwner( Long courseId, String client);
    void approveCourseByClient(String client, Long courseId);
}
