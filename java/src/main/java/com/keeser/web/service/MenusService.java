package com.keeser.web.service;


import com.alibaba.fastjson.JSONObject;
import com.keeser.web.common.ResultCode;
import com.keeser.web.common.ResultMetaJson;
import com.keeser.web.dao.PermissionApiDAO;
import com.keeser.web.dao.PermissionDAO;
import com.keeser.web.entity.Permission;
import com.keeser.web.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;


// 菜单service

@Service
public class MenusService {
    @Autowired
    PermissionDAO permissionDAO;
    @Autowired
    RolesService rolesService;

    // 将取到的数据进行嵌套处理, 返回菜单json
    public JSONObject getMenus(){
        List<Permission> menus = null;
        // 获取菜单
        try {
            menus = this.permissionDAO.findAllByPermissionApiExists();
        }catch (Exception e){
            return new ResultMetaJson(ResultCode.STATUS_BAD_REQUEST, "获取菜单列表发生异常").getMetaJson();
        }

        if(menus == null){
            return new ResultMetaJson(ResultCode.STATUS_BAD_REQUEST, "获取菜单列表发生异常").getMetaJson();
        }

        JSONObject ret_json = new ResultMetaJson(ResultCode.STATUS_OK, "获取菜单列表成功").getMetaJson();

        // 用hashmap存储子目录和Permission对象
        HashMap<Integer, ArrayList<JSONObject>> childMenu = new HashMap<>();
        HashMap<Integer, Permission> permissionHashMap = new HashMap<>();

        ArrayList<Integer> ids = getMenuIds();

        ArrayList<JSONObject> data_array = new ArrayList<>();

        // 第一次遍历菜单
        for(Permission permission: menus){
            int pid = permission.getPsId();
            // 过滤暂时未实现的功能
            if(pid == 115 || pid == 121)
                continue;

            // 将对应的对象放进哈希表中
            permissionHashMap.put(pid, permission);
        }

        // 第二次遍历菜单
        for(Integer id: ids){
            Permission permission = permissionHashMap.get(id);
            if(permission == null){
                continue;
            }
            JSONObject temp = new JSONObject();
            temp.put("id", permission.getPsId());
            temp.put("authName", permission.getPsName());
            temp.put("path", permission.getPermissionApi().getPsApiPath());
            int psPid = permission.getPsPid();
            if(permission.getPsLevel() == 0){  // 0级目录
                int pid = permission.getPsId();
                data_array.add(temp);
            }else if(permission.getPsLevel() == 1) {  // 1级目录
                childMenu.computeIfAbsent(psPid, k -> new ArrayList<JSONObject>());
                childMenu.get(psPid).add(temp);
            }
        }

        // 遍历data
        for(JSONObject json: data_array){
            ArrayList<JSONObject> tmp_array = childMenu.get(json.getInteger("id"));
            if(tmp_array != null){
                json.put("children", tmp_array);
            }

        }
        ret_json.put("data", data_array);

        return ret_json;
    }

    private ArrayList<Integer> getMenuIds(){
        // 获取当前用户的role id
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User)authentication.getPrincipal();
        return rolesService.getPsIdsArrayByRoleId(user.getRoleId());
    }

}
