package it.academy.FinalProject.Service.ServiceImpl;

import it.academy.FinalProject.Entity.Donation;
import it.academy.FinalProject.Entity.User;
import it.academy.FinalProject.Enum.DonationStatus;
import it.academy.FinalProject.Repository.DonationRepo;
import it.academy.FinalProject.Service.DonationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormatSymbols;
import java.time.LocalDate;
import java.util.*;

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

    @Override
    public List<Integer> getDonationCountByMonth() {
        Date toDate = new Date();
        LinkedList<Integer> listOfCount = new LinkedList<>();
        for (int i = 1; i <= 12; i++) {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MONTH, -i);
            Date fromDate = cal.getTime();
            List<Donation> list = donationRepo.findAllByCreatedDateLessThanEqualAndCreatedDateGreaterThanEqual(toDate, fromDate);
            int sum = 0;
            for (Donation d : list) {
                sum += d.getAmount();
            }
            listOfCount.addFirst(sum);
            toDate = fromDate;
        }
        return listOfCount;
    }

    @Override
    public List<String> getAllMonth() {
        List<String> monthList = new ArrayList<>(Arrays.asList(new DateFormatSymbols().getMonths()));
        LinkedList<String> mList = new LinkedList<>();
        LocalDate d = LocalDate.now();
        String dow = String.valueOf(d.getMonth());
        int x = monthList.indexOf(dow);
        for (int i = 0; i < 11; i++) {
            if (x < 0) {
                x = 12;
            }
            mList.addFirst(monthList.get(x));
            x--;
        }
        return mList;

    }

    @Override
    public Integer getDonationTotalCount() {
        List<Integer> list = getDonationCountByMonth();
        int sum = 0;
        for (Integer i : list) {
            sum += i;
        }
        return sum;
    }
}
