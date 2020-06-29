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

@Entity
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "employee")
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column(name = "login", unique = true, nullable = false)
    String login;

    @Column(name = "password", nullable = false)
    @NotNull
    String password;
    

    @Column(name = "name", nullable = false)
    String name;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    Gender gender;

    @Email
    @Column(name = "email", nullable = false, unique = true)
    @NotNull
    String email;

    @Column(name = "created_Date", updatable = false, nullable = false)
    @CreationTimestamp
    LocalDateTime createdDate;

    @ManyToOne()
    @JoinColumn(name="role_id")
    Role role;
}
