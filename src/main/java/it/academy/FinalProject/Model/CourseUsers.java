package it.academy.FinalProject.Model;

import it.academy.FinalProject.Entity.Course;
import it.academy.FinalProject.Entity.User;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.util.List;
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CourseUsers {
    Course course;
    User users;
}
