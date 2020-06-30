package it.academy.FinalProject.Service.ServiceImpl;

import it.academy.FinalProject.Entity.*;
import it.academy.FinalProject.Enum.ApprovalStatus;
import it.academy.FinalProject.Enum.CourseStatus;
import it.academy.FinalProject.Model.RegisterUser;
import it.academy.FinalProject.Repository.*;
import it.academy.FinalProject.Service.EmployeeService;
import it.academy.FinalProject.Service.RoleService;
import it.academy.FinalProject.Service.UserService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserServiceImpl implements UserService {
    @Autowired
    SessionRegistry sessionRegistry;
    @Autowired
    UserRepo userRepo;
    @Autowired
    RoleRepo roleRepo;
    @Autowired
    RoleService roleService;
    @Autowired
    CourseRepo courseRepo;
    @Autowired
    ApprovalRepo approvalRepo;
    @Autowired
    CourseUserStatusRepo courseUserStatusRepo;
    @Autowired
    EmplRepo emplRepo;
    @Autowired
    EmployeeService employeeService;


    @Override
    public List<User> getAll() {
        List<User> u = userRepo.findAll();
        for (User us : u) {
            if (findCourses(us.getId()) != null) {
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
        if (userRepo.existsById(userRepo.findByLogin(login).getId())) {
            userRepo.deleteById(userRepo.findByLogin(login).getId());
        }
    }

    @Override
    public List<User> findAllByRole(String role) {
        return userRepo.findAllByRole(roleService.findByName("ROLE_" + role.toUpperCase()).getId());
    }

    @Override
    public void offerCourse(Course course) {
        courseRepo.save(course);
    }

    @Override
    public void finishCourse(Course course, String login) {
        User u = userRepo.findByLogin(login);
        User owner = course.getAuthor();
        Approval a = approvalRepo.findApprovalByClientAndCourse(u.getId(), course.getId());
        if (a.getClientApproval() && a.getOwnerApproval()) {
            owner.setBalance(owner.getBalance() + a.getDepositCost());
            a.setApprovalStatus(ApprovalStatus.APPROVED);
            userRepo.save(owner);
            CourseUserStatus cu = courseUserStatusRepo.findByCourseAndUser(u.getId(), course.getId());
            cu.setCourseStatus(CourseStatus.COMPLETED);
            courseUserStatusRepo.save(cu);
        }
        approvalRepo.save(a);
    }

    @Override
    public void finishCourseByAdmin(Course course) {
        List<Approval> approvalList = approvalRepo.findAllByCourse(course);
        for (Approval a : approvalList) {
            if (a.getClientApproval() && !a.getOwnerApproval()){
                User u = a.getClient();
                u.setBalance(u.getBalance() + a.getDepositCost());
                userRepo.save(u);
                CourseUserStatus cu = courseUserStatusRepo.findByCourseAndUser(u.getId(), course.getId());
                cu.setCourseStatus(CourseStatus.DEACTIVATED);
                courseUserStatusRepo.save(cu);
            }
            a.setApprovalStatus(ApprovalStatus.REJECTED);
            approvalRepo.save(a);
        }
    }

    @Override
    public void sendRequest(Course course, String login) {
        User user = userRepo.findByLogin(login);
        if (course.getFreePlaces() != 0 && !user.getRequestedCourses().contains(course)
                && user.getBalance() >= course.getCost()) {
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
        user.getRequestedCourses().remove(course);
        save(user);
        CourseUserStatus c = courseUserStatusRepo.findByCourseAndUser(user.getId(), course.getId());
        c.setCourseStatus(CourseStatus.AVAILABLE);
        courseUserStatusRepo.save(c);
    }

    @Override
    public void approveRequest(Course course, String owner, String client) {
        if (userRepo.findByLogin(owner).getId().equals(course.getAuthor().getId()) && userRepo.findByLogin(client).getBalance() >= course.getCost()) {
            userRepo.findByLogin(client).getCourseGet().add(course);
            userRepo.save(userRepo.findByLogin(client));
            CourseUserStatus c = courseUserStatusRepo.findByCourseAndUser(userRepo.findByLogin(client).getId(), course.getId());
            c.setCourseStatus(CourseStatus.ENROLLED);
            courseUserStatusRepo.save(c);
            course.setFreePlaces(course.getFreePlaces() - 1);
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
    public void rejectRequest(Course course, String owner, String client) {
        CourseUserStatus c = courseUserStatusRepo.findByCourseAndUser(userRepo.findByLogin(client).getId(), course.getId());
        c.setCourseStatus(CourseStatus.REJECTED);
        courseUserStatusRepo.save(c);
    }

    @Override
    public Long findCourses(Long id) {
        return userRepo.findCourses(id);
    }

    @Override
    public Boolean checkIfComplete(Long course, Long user) {
        Approval a = approvalRepo.findApprovalByClientAndCourse(user, course);
        return a.getOwnerApproval() && a.getClientApproval();
    }

    @Override
    public User saveModelUser(RegisterUser u) {
        User user = new User();
        user.setId(u.getId());
        user.setPassword(u.getPassword());
        user.setLogin(u.getLogin());
        user.setName(u.getName());
        user.setIsActive(true);
        user.setGender(u.getGender());
        user.setCreatedDate(u.getCreatedDate());
        user.setRole(roleRepo.findByName("ROLE_USER"));
        user.setBalance((long) 100);
        user.setEmail(u.getEmail());
        return userRepo.save(user);
    }

    @Override
    public Integer getLoggedInUsers() {
        List<UserDetails> allPrincipals = sessionRegistry.getAllPrincipals()
                .stream()
                .filter(principal -> principal instanceof UserDetails)
                .map(UserDetails.class::cast)
                .collect(Collectors.toList());
        return allPrincipals.size() - 1;
    }

    @Override
    public Integer getLoggedInEmployees() {
        Integer count = 0;
        List<UserDetails> allPrincipals = sessionRegistry.getAllPrincipals()
                .stream()
                .filter(principal -> principal instanceof UserDetails)
                .map(UserDetails.class::cast)
                .collect(Collectors.toList());
        for (UserDetails u : allPrincipals) {
            count = emplRepo.findAll().contains(emplRepo.findByLogin(u.getUsername())) ? count + 1 : count;
        }
        return count;
    }


    @Override
    public Integer getFemaleUserCount() {
        return userRepo.findByGender("FEMALE");
    }

    @Override
    public Integer getMaleUserCount() {
        return userRepo.findByGender("MALE");
    }

    @Override
    public List<String> getDayOfWeek() {
        String[] dayOfWeekList = {"MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY"};
        List<String> arrayList = new ArrayList<>(Arrays.asList(dayOfWeekList));
        LinkedList<String> dowList = new LinkedList<>();
        LocalDate d = LocalDate.now();
        String dow = String.valueOf(d.getDayOfWeek());
        int x = arrayList.indexOf(dow);
        for (int i = 1; i <= 7; i++) {
            if (x < 0) {
                x = 6;
            }
            dowList.addFirst(arrayList.get(x));
            x--;
        }
        return dowList;
    }

    @Override
    public List<Integer> getUserCountByDOW() {
        Date toDate = new Date();
        LinkedList<Integer> listOfCount = new LinkedList<>();
        for (int i = 1; i <= 7; i++) {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -i);
            Date fromDate = cal.getTime();
            Integer size = userRepo.findAllByCreatedDateLessThanEqualAndCreatedDateGreaterThanEqual(toDate, fromDate).size();
            if (size > 0) {
                size = size - employeeService.getAll().size() - 1;
                System.err.println("entered");
            }
            listOfCount.addFirst(size);
            System.err.println(listOfCount);
            toDate = fromDate;
        }
        return listOfCount;
    }

    @Override
    public Integer getUserTotalCountByWeek() {
        List<Integer> integerList = getUserCountByDOW();
        int sum = 0;
        for (Integer i : integerList) {
            sum += i;
        }
        return sum;
    }
}
