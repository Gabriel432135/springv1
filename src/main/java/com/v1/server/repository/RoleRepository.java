package com.v1.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.v1.server.model.Role;
import java.util.List;


public interface RoleRepository extends JpaRepository<Role, Long>{
    public Role findByRoleName(String roleName);
    public List<Role> findByRoleNameIn(List<String> roleNames);
}
