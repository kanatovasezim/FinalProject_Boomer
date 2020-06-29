package it.academy.FinalProject.Repository;

import it.academy.FinalProject.Entity.Donation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface DonationRepo extends JpaRepository<Donation, Long> {
    List<Donation> findAllByCreatedDateLessThanEqualAndCreatedDateGreaterThanEqual( Date toDate, Date fromDate);
}
