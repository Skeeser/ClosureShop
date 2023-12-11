package com.keeser.web.dao;


import com.keeser.web.entity.Roles;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.JpaRepository;

// 权限的DAO

@EntityScan("com.keeser.web.entity.Roles")
public interface RolesDAO extends JpaRepository<Roles, Integer> {
    Roles findByRoleId(int roleId);
}
