package it.academy.FinalProject.Service.ServiceImpl;

import it.academy.FinalProject.Entity.Donation;
import it.academy.FinalProject.Enum.DonationStatus;
import it.academy.FinalProject.Repository.DonationRepo;
import it.academy.FinalProject.Service.DonationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DonationServiceImpl implements DonationService {
    @Autowired
    private DonationRepo donationRepo;

    @Override
    public List<Donation> getAll() {
        return donationRepo.findAll();
    }

    @Override
    public Donation getById(Long id) {
        return donationRepo.findById(id).orElse(new Donation());
    }

    @Override
    public Donation save(Donation donation) {
        donation.setDonationStatus(DonationStatus.REGISTERED);
        return donationRepo.save(donation);
    }

    @Override
    public void delete(Long id) {
        donationRepo.deleteById(id);
    }
}
