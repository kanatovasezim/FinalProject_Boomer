package it.academy.FinalProject.Service.ServiceImpl;

import it.academy.FinalProject.Entity.*;
import it.academy.FinalProject.Enum.ApprovalStatus;
import it.academy.FinalProject.Enum.CourseStatus;
import it.academy.FinalProject.Model.LoginUser;
import it.academy.FinalProject.Model.RegisterEmpl;
import it.academy.FinalProject.Model.RegisterUser;
import it.academy.FinalProject.Repository.*;
import it.academy.FinalProject.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepo userRepo;
    @Autowired
    RoleRepo roleRepo;
    @Autowired
    CourseRepo courseRepo;
    @Autowired
    ApprovalRepo approvalRepo;
    @Autowired
    CourseUserStatusRepo courseUserStatusRepo;
    @Autowired
    EmplRepo emplRepo;

    @Override
    public List<User> getAll() {
        List<User> u = userRepo.findAll();
        for (User us: u) {
            if (findCourses(us.getId()) != null){
                us.setCourseGet((List<Course>) courseRepo.findById(findCourses(us.getId())).orElse(new Course()));
            }
        }
        return u;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepo.findByLogin(s);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getLogin(),
                user.getPassword(),
                mapRolesToAuthorities(user.getRole()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Role role) {
        SimpleGrantedAuthority auth = new SimpleGrantedAuthority(role.getRoleName());
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(auth);
        return authorities;
    }

    @Override
    public User getById(Long id) {
        return userRepo.findById(id).orElse(new User());
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
        Approval a = approvalRepo.findApprovalByClientAndCourse(u.getId(),course.getId());
        if (a.getClientApproval() && a.getOwnerApproval()){
        owner.setBalance(owner.getBalance()+a.getDepositCost());
            a.setApprovalStatus(ApprovalStatus.APPROVED);
            CourseUserStatus c = courseUserStatusRepo.findByCourseAndUser( userRepo.findByLogin(login).getId(), course.getId());
            c.setCourseStatus(CourseStatus.COMPLETED);
            courseUserStatusRepo.save(c);
            userRepo.save(owner);
            CourseUserStatus cu = courseUserStatusRepo.findByCourseAndUser(course.getId(),u.getId());
            cu.setCourseStatus(CourseStatus.COMPLETED);
            courseUserStatusRepo.save(cu);
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
            CourseUserStatus c = CourseUserStatus.builder()
                    .course(course)
                    .user(user)
                    .courseStatus(CourseStatus.REQUESTED)
                    .build();
            courseUserStatusRepo.save(c);
            user.getRequestedCourses().add(course);
            course.getRequests().add(user);
            userRepo.save(user);
            courseRepo.save(course);
        }
    }

    @Override
    public void removeRequest(Course course, String login) {
        User user = userRepo.findByLogin(login);
        CourseUserStatus c = courseUserStatusRepo.findByCourseAndUser(user.getId(), course.getId());
        c.setCourseStatus(CourseStatus.AVAILABLE);
        courseUserStatusRepo.save(c);
    }

    @Override
    public void approveRequest(Course course, String owner, String client) {
        if (userRepo.findByLogin(owner).getId().equals(course.getAuthor().getId()) && userRepo.findByLogin(client).getBalance() >= course.getCost()){
            userRepo.findByLogin(client).getCourseGet().add(course);
            userRepo.save(userRepo.findByLogin(client));
            CourseUserStatus c = courseUserStatusRepo.findByCourseAndUser(userRepo.findByLogin(client).getId(), course.getId());
            c.setCourseStatus(CourseStatus.ENROLLED);
           courseUserStatusRepo.save(c);
            course.setFreePlaces(course.getFreePlaces()-1);
            course.getRequests().remove(userRepo.findByLogin(client));
            courseRepo.save(course);
            Approval a = Approval.builder()
                    .course(course)
                    .client(userRepo.findByLogin(client))
                    .clientApproval(false)
                    .ownerApproval(false)
                    .depositCost(course.getCost())
                    .build();
            approvalRepo.save(a);
            User u = userRepo.findByLogin(client);
            u.setBalance(userRepo.findByLogin(client).getBalance() - course.getCost());
            userRepo.save(u);
        }
    }

    @Override
    public Long findCourses(Long id) {
        return userRepo.findCourses(id);
    }

    @Override
    public User findByEmail(String login) {
        return userRepo.findByEmail(login);
    }



    @Override
    public User saveModelUser(RegisterUser u) {
        User user = new User();
        user.setId(u.getId());
        user.setPassword(u.getPassword());
        user.setLogin(u.getLogin());
        user.setName(u.getName());
        user.setIsActive(true);
        user.setCreatedDate(u.getCreatedDate());
        user.setRole(roleRepo.findByName("ROLE_USER"));
        user.setBalance((long) 100);
        user.setEmail(u.getEmail());
        return userRepo.save(user);
    }
}
