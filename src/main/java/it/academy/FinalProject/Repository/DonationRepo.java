package it.academy.FinalProject.Repository;

import it.academy.FinalProject.Entity.Donation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DonationRepo extends JpaRepository<Donation, Long> {
}
