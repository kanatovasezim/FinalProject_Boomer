package it.academy.FinalProject.Service;

import it.academy.FinalProject.Entity.User;

import java.util.List;

public interface UserService {
    List<User> getAll();
    User getById(Long id);
    User save(User user);
    void delete(Long id);
    User findByLogin(String login);
    void deleteByLogin(String login);
}
