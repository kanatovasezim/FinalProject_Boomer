package it.academy.FinalProject.Service;

import it.academy.FinalProject.Entity.Donation;

import java.util.List;

public interface DonationService {
    List<Donation> getAll();
    Donation getById(Long id);
    Donation save(Donation donation);
    void delete(Long id);
}
