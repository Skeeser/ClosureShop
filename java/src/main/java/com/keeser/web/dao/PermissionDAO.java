package com.keeser.web.dao;

import com.keeser.web.entity.Permission;
import com.keeser.web.entity.User;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;


@EntityScan("com.keeser.web.entity.Permission")
public interface PermissionDAO extends JpaRepository<Permission, Integer>{
    Permission findByPsId(int psId);

//    @Query(nativeQuery = true,
//        value = "SELECT id, api.ps_id, ps_api_path, ps_name, ps_pid, ps_level " +
//                "FROM sp_permission_api as api LEFT JOIN sp_permission as main ON main.ps_id = api.ps_id " +
//                "WHERE main.ps_id is not null;")
    List<Permission> findAll();
}
