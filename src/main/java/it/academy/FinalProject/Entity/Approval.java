package it.academy.FinalProject.Entity;

import it.academy.FinalProject.Enum.ApprovalStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Entity
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "approval")
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Approval {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    @Column(name = "ownerApproval", nullable = false)
    Boolean ownerApproval;

    @Column(name = "clientApproval", nullable = false)
    Boolean clientApproval;

    @Column(name = "depositCost")
    Integer depositCost;

    @ManyToOne
    @JoinColumn(name = "client")
    User client;

    @ManyToOne
    @JoinColumn(name = "course")
    Course course;

    @Column(name = "approvalStatus")
    @Enumerated(EnumType.STRING)
    ApprovalStatus approvalStatus;

}
