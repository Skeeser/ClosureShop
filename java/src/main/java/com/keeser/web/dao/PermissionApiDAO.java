package com.keeser.web.dao;

import com.keeser.web.entity.Permission;
import com.keeser.web.entity.PermissionApi;
import com.keeser.web.entity.User;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;


@EntityScan("com.keeser.web.entity.PermissionApi")
public interface PermissionApiDAO extends JpaRepository<PermissionApi, Integer>{
    // 使用原生sql查询
//    @Query(nativeQuery = true,
//            value = "SELECT * FROM sp_permission_api as api LEFT JOIN sp_permission as main ON main.ps_id = api.ps_id " +
//                    "WHERE main.ps_id is not null;")
    List<PermissionApi> findAll();

}
