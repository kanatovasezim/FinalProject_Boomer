package it.academy.FinalProject.Entity;

import it.academy.FinalProject.Enum.DonationStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
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

    @Column(name = "name")
    String name;

    @Column(name = "email")
    String email;

    @Column(name = "phone_number")
    String phoneNumber;

    @Column(name = "amount")
    Integer amount;

    @Column(name = "comment")
    String comment;

    @Column(name = "anonymity")
    Boolean anonymity;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    DonationStatus donationStatus;

    @Column(name = "created_Date", updatable = false)
    @CreationTimestamp
    Date createdDate;
}
