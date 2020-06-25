package it.academy.FinalProject.Repository;

import it.academy.FinalProject.Entity.Approval;
import it.academy.FinalProject.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ApprovalRepo extends JpaRepository<Approval, Long> {
    @Query( value = "select * from approval a where a.client = :client and a.course = :course", nativeQuery=true)
    Approval findApprovalByClientAndCourse(@Param("client") Long clientId, @Param("course") Long courseId);
}
