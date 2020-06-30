package it.academy.FinalProject.Repository;

import it.academy.FinalProject.Entity.Approval;
import it.academy.FinalProject.Entity.Course;
import it.academy.FinalProject.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApprovalRepo extends JpaRepository<Approval, Long> {
    @Query( value = "select * from approval a where a.client = :client and a.course = :course", nativeQuery=true)
    Approval findApprovalByClientAndCourse(@Param("client") Long clientId, @Param("course") Long courseId);
    List<Approval> findAllByCourse(Course course);
    @Query( value = "select * from approval a where a.course = :courseId and a.client_approval = :clientA and a.owner_approval = :ownerA", nativeQuery=true)
    List<Approval> findApprovalByAuthorAndStatus(@Param("courseId") Long courseId, @Param("clientA") Boolean clientApproval, @Param("ownerA") Boolean ownerApproval);
}
