package it.academy.FinalProject.Entity;

import it.academy.FinalProject.Enum.DonationStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Entity
@Builder
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "donation")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Donation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    @NotEmpty
    @Column(name = "name")
    String name;
    @NotEmpty
    @Column(name = "email", unique = true, nullable = false)
    String email;
    @NotEmpty
    @Column(name = "phone_number")
    String phoneNumber;
    @NotEmpty
    @Column(name = "amount")
    Integer amount;
    @NotEmpty
    @Column(name = "comment")
    String comment;
    @Column(name = "anonymity")
    Boolean anonymity;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    DonationStatus donationStatus;
    @NotEmpty
    @Column(name = "created_Date", updatable = false)
    @CreationTimestamp
    Date createdDate;
}
