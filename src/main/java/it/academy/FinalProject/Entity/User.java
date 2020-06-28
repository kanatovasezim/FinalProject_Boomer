package it.academy.FinalProject.Entity;
import com.sun.istack.NotNull;
import it.academy.FinalProject.Enum.CourseStatus;
import it.academy.FinalProject.Enum.Gender;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.*;
import javax.validation.constraints.Email;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Builder
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "s_user")
@FieldDefaults(level = AccessLevel.PRIVATE)
//User: id, login, password, dateOfBirth, email, role, list<Course> courseOffered, list<Course> courseGet, balance, created_date
public class User {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    Long id;

    @Column(name = "login", unique = true, nullable = false)
    String login;

    @Column(name = "password", nullable = false)
    @NotNull
    String password;

    @Column(name = "name")
    String name;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    Gender gender;

    @Email
    @Column(name = "email", unique = true)
    @NotNull
    String email;

    @Column(name = "balance")
    @NotNull
    Long balance;

    @Column(name = "is_Active")
    Boolean isActive;

    @Column(name = "created_Date", updatable = false, nullable = false)
    @CreationTimestamp
    LocalDateTime createdDate;

    @ManyToMany( cascade=CascadeType.ALL)
    @JoinTable(name="course_Got", joinColumns=@JoinColumn(name="s_user_id"), inverseJoinColumns=@JoinColumn(name="course_id"))
    List<Course> courseGet;

    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(name="requestedCourses", joinColumns=@JoinColumn(name="s_user_id"), inverseJoinColumns=@JoinColumn(name="course_id"))
    List<Course> requestedCourses;

    @ManyToOne()
    @JoinColumn(name="role_id")
    Role role;
}
