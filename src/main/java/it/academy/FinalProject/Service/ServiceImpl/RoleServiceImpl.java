package it.academy.FinalProject.Service.ServiceImpl;

import it.academy.FinalProject.Entity.Role;
import it.academy.FinalProject.Repository.RoleRepo;
import it.academy.FinalProject.Service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    RoleRepo roleRepo;

    @Override
    public List<Role> getAll() {
        return roleRepo.findAll();
    }

    @Override
    public Role getById(Long id) {
        return roleRepo.findById(id).get();
    }

    @Override
    public Role save(Role role) {
        return roleRepo.save(role);
    }

    @Override
    public void delete(Long id) {
        roleRepo.deleteById(id);
    }

    public Role findByName(String role){
        return roleRepo.findByName(role);
    }
}
