package com.keeser.web.service;

import com.keeser.web.dao.RolesDAO;
import com.keeser.web.entity.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class RolesService {
    @Autowired
    RolesDAO rolesDAO;

    // 获取ids string格式
    public String getPsIdsByRoleId(int roleId){
        Roles  roles = rolesDAO.findByRoleId(roleId);
        return roles.getPsIds();
    }

    // 获取ids array格式
    public ArrayList<Integer> getPsIdsArrayByRoleId(int roleId){
        Roles roles = rolesDAO.findByRoleId(roleId);
        ArrayList<Integer> psIdsArray = new ArrayList<>();
        String[] tmpArray = roles.getPsIds().split(",");
        for(String str: tmpArray){
            psIdsArray.add(Integer.parseInt(str));
        }
        return psIdsArray;
    }
}
