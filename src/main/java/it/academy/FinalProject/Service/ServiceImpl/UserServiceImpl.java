package it.academy.FinalProject.Service.ServiceImpl;

import it.academy.FinalProject.Entity.Approval;
import it.academy.FinalProject.Entity.Course;
import it.academy.FinalProject.Entity.User;
import it.academy.FinalProject.Enum.ApprovalStatus;
import it.academy.FinalProject.Repository.ApprovalRepo;
import it.academy.FinalProject.Repository.CourseRepo;
import it.academy.FinalProject.Repository.UserRepo;
import it.academy.FinalProject.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepo userRepo;
    @Autowired
    CourseRepo courseRepo;
    @Autowired
    ApprovalRepo approvalRepo;

    @Override
    public List<User> getAll() {
        return userRepo.findAll();
    }

    @Override
    public User getById(Long id) {
        return userRepo.findById(id).get();
    }

    @Override
    public User save(User user) {
        return userRepo.save(user);
    }

    @Override
    public void delete(Long id) {
        userRepo.deleteById(id);
    }
    @Override
    public User findByLogin(String login) {
        return userRepo.findByLogin(login);
    }
    @Override
    public void deleteByLogin(String login) {
        if (userRepo.existsById(userRepo.findByLogin(login).getId())){
            userRepo.deleteById(userRepo.findByLogin(login).getId());
        }
    }

    @Override
    public void offerCourse(Course course) {
        courseRepo.save(course);
    }

    @Override
    public void finishCourse(Course course, String login) {
        User u = userRepo.findByLogin(login);
        User owner = course.getAuthor();
        Approval a = approvalRepo.findApprovalByClientAndCourse(login,course.getId());
        if (a.getClientApproval() && a.getOwnerApproval()){
        owner.setBalance(owner.getBalance()+a.getDepositCost());
            a.setApprovalStatus(ApprovalStatus.APPROVED);
            userRepo.save(owner);
        } else {
            u.setBalance(u.getBalance() + a.getDepositCost());
            a.setApprovalStatus(ApprovalStatus.REJECTED);
            userRepo.save(u);
        }
        approvalRepo.save(a);
    }

    @Override
    public void sendRequest(Course course, String login) {
        User user = userRepo.findByLogin(login);
        if (course.getFreePlaces()!=0 && !user.getRequestedCourses().contains(course)
        && user.getBalance() >= course.getCost()){
            user.getRequestedCourses().add(course);
            course.getRequests().add(user);
            userRepo.save(user);
            courseRepo.save(course);
        }
    }

    @Override
    public void approveRequest(Course course, String owner, String client) {
        if (userRepo.findByLogin(owner).getId().equals(course.getAuthor().getId()) && userRepo.findByLogin(client).getBalance() >= course.getCost()){
            userRepo.findByLogin(client).getCourseGet().add(course);
            userRepo.save(userRepo.findByLogin(client));
            course.setFreePlaces(course.getFreePlaces()-1);
            course.getRequests().remove(userRepo.findByLogin(client));
            courseRepo.save(course);
            Approval a = Approval.builder()
                    .course(course)
                    .client(userRepo.findByLogin(client))
                    .depositCost(course.getCost())
                    .build();
            approvalRepo.save(a);
            User u = userRepo.findByLogin(client);
            u.setBalance(userRepo.findByLogin(client).getBalance() - course.getCost());
            userRepo.save(u);
        }
    }
}
