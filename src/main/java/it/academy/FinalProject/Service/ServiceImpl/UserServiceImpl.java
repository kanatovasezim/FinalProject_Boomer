package it.academy.FinalProject.Service.ServiceImpl;

import it.academy.FinalProject.Entity.User;
import it.academy.FinalProject.Repository.UserRepo;
import it.academy.FinalProject.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepo userRepo;

    @Override
    public List<User> getAll() {
        return userRepo.findAll();
    }

    @Override
    public User getById(Long id) {
        return userRepo.findById(id).get();
    }

    @Override
    public User save(User user) {
        return userRepo.save(user);
    }

    @Override
    public void delete(Long id) {
        userRepo.deleteById(id);
    }
    @Override
    public User findByLogin(String login) {
        return userRepo.findByLogin(login);
    }
    @Override
    public void deleteByLogin(String login) {
        if (userRepo.existsById(userRepo.findByLogin(login).getId())){
            userRepo.deleteById(userRepo.findByLogin(login).getId());
        }
    }
}
