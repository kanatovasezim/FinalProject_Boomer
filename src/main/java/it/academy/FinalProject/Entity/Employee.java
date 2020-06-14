package it.academy.FinalProject.Entity;

import com.sun.istack.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;

@Entity
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "employee")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column(name = "login", unique = true, nullable = false)
    @NotNull
    String login;

    @Column(name = "password")
    @NotNull
    String password;

    @Column(name = "name")
    @NotNull
    String name;

    @Column(name = "date_Of_Birth")
    @NotNull
    Date dateOfBirth;

    @Column(name = "email")
    @NotNull
    String email;

    @Column(name = "is_Active")
    Boolean isActive;

    @Column(name = "created_Date")
    @CreatedDate
    Date createdDate;
}
