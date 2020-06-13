package it.academy.FinalProject.Entity;

import it.academy.FinalProject.Enum.UserRole;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_u")
@FieldDefaults(level = AccessLevel.PRIVATE)
//User: id, login, password, dateOfBirth, email, role, list<Course> courseOffered, list<Course> courseGet, balance, created_date
public class User {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    Long id;

    @Column(name = "login")
    String login;

    @Column(name = "password")
    String password;

    @Column(name = "date_Of_Birth")
    Date dateOfBirth;

    @Column(name = "email")
    String email;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Column(name = "course_Offer")
    List<Course> courseOffer;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Column(name = "course_Get")
    List<Course> courseGet;

    @Column(name = "balance")
    Long balance;

    @Column(name = "is_Active")
    Boolean isActive;

    @Column(name = "created_Date")
    @CreatedDate
    Date createdDate;

}
