package com.keeser.web.dao;

import com.keeser.web.entity.Permission;
import com.keeser.web.entity.User;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


@EntityScan("com.keeser.web.entity.Permission")
public interface PermissionDAO extends JpaRepository<Permission, Integer>{
    Permission findByPsId(int psId);

    List<Permission> findAll();
}
