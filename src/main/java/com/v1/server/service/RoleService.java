package com.v1.server.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.v1.server.model.Role;
import com.v1.server.repository.RoleRepository;

@Service
public class RoleService {
    @Autowired
    RoleRepository roleRepository;

    public List<Role> listarRoles(){
        return roleRepository.findAll();
    }

    public Role buscarRolePorNome(String nome){
        return roleRepository.findByRoleName(nome);
    }

    public List<Role> listarRolesPorNomes(List<String> roleNames){
        return roleRepository.findByRoleNameIn(roleNames);
    }

    public Role salvarRole(Role role){
        return roleRepository.save(role);
    }

    public Role buscarRoleById(Long id){
        return roleRepository.findById(id).orElse(null);
    }

    public void deletarRole(Long id){
        roleRepository.deleteById(id);
    }

}
