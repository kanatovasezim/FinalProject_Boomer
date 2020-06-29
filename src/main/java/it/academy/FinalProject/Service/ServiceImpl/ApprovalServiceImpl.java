package it.academy.FinalProject.Service.ServiceImpl;

import it.academy.FinalProject.Entity.Approval;
import it.academy.FinalProject.Entity.Course;
import it.academy.FinalProject.Model.ApprovalUser;
import it.academy.FinalProject.Repository.ApprovalRepo;
import it.academy.FinalProject.Service.ApprovalService;
import it.academy.FinalProject.Service.CourseService;
import it.academy.FinalProject.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ApprovalServiceImpl implements ApprovalService {
    @Autowired
    private ApprovalRepo approvalRepo;
    @Autowired
    private UserService userService;
    @Autowired
    private CourseService courseService;

    @Override
    public void approveCourseByOwner(Long courseId, String client) {
        Approval a = approvalRepo.findApprovalByClientAndCourse(userService.findByLogin(client).getId(), courseId);
        a.setOwnerApproval(true);
        approvalRepo.save(a);
        if (userService.checkIfComplete(courseId, userService.findByLogin(client).getId())) {
            System.out.println("Owner: " + userService.checkIfComplete(courseId, userService.findByLogin(client).getId()));
            userService.finishCourse(courseService.getById(courseId), client);
        }
    }


    @Override
    public void approveCourseByClient(String client, Long courseId) {
        Approval a = approvalRepo.findApprovalByClientAndCourse(userService.findByLogin(client).getId(), courseId);
        a.setClientApproval(true);
        approvalRepo.save(a);
        if (userService.checkIfComplete(courseId, userService.findByLogin(client).getId())) {
            System.out.println("Client: " + userService.checkIfComplete(courseId, userService.findByLogin(client).getId()));
            userService.finishCourse(courseService.getById(courseId), client);
        }
    }

    @Override
    public List<ApprovalUser> findApprovalsByClient(String login) {
        List<Course> courseList = courseService.findOfferingCourses(login);
        List<ApprovalUser> approvalUserList = new ArrayList<>();
        for (Course c : courseList) {
            List<Approval> approvals = approvalRepo.findApprovalByAuthorAndStatus(c.getId(), true, false);
            for (Approval a : approvals) {
                ApprovalUser au = ApprovalUser.builder()
                        .course(c)
                        .users(a.getClient())
                        .build();
                approvalUserList.add(au);
            }
        }
        return approvalUserList;
    }
}
