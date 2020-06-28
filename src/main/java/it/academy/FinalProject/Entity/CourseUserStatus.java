package it.academy.FinalProject.Entity;
import it.academy.FinalProject.Enum.CourseStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "course_status")
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CourseUserStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @OneToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    @OneToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "course_id", nullable = false)
    Course course;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    CourseStatus courseStatus;
}
