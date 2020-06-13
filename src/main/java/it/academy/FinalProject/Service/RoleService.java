package it.academy.FinalProject.Service;

import it.academy.FinalProject.Entity.Role;

import java.util.List;

public interface RoleService {
    List<Role> getAll();
    Role getById(Long id);
    Role save(Role role);
    void delete(Long id);
}
