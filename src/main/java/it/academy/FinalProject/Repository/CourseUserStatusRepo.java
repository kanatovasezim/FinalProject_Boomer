package it.academy.FinalProject.Repository;

import it.academy.FinalProject.Entity.CourseUserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseUserStatusRepo extends JpaRepository<CourseUserStatus, Long> {
}
