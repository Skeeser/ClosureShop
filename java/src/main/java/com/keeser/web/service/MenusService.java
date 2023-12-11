package com.keeser.web.service;


import com.alibaba.fastjson.JSONObject;
import com.keeser.web.common.ResultCode;
import com.keeser.web.common.ResultMetaJson;
import com.keeser.web.dao.PermissionApiDAO;
import com.keeser.web.dao.PermissionDAO;
import com.keeser.web.entity.Permission;
import org.springframework.beans.factory.annotation.Autowired;
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

    // 将取到的数据进行嵌套处理, 返回菜单json
    public JSONObject getMenus(){
        List<Permission> menus = null;
        // 获取菜单
        try {
            menus = this.permissionDAO.findAll();
        }catch (Exception e){
            return new ResultMetaJson(ResultCode.STATUS_BAD_REQUEST, "获取菜单列表发生异常").getMetaJson();
        }

        if(menus == null){
            return new ResultMetaJson(ResultCode.STATUS_BAD_REQUEST, "获取菜单列表发生异常").getMetaJson();
        }

        JSONObject ret_json = new ResultMetaJson(ResultCode.STATUS_OK, "获取菜单列表成功").getMetaJson();

        // 用hashmap存储子目录
        HashMap<Integer, ArrayList<JSONObject>> childMenu = new HashMap<>();

        // 获取
        ArrayList<JSONObject> dara_array = new ArrayList<>();
        // 遍历菜单
        for(Permission permission: menus){
            JSONObject temp = new JSONObject();
            temp.put("id", permission.getPsId());
            temp.put("authName", permission.getPsName());
            temp.put("path", permission.getPermissionApi().getPsApiPath());
            int psPid = permission.getPsPid();
            if(permission.getPsLevel() == 0){  // 0级目录
                dara_array.add(temp);

            }else if(permission.getPsLevel() == 1) {  // 1级目录
                childMenu.computeIfAbsent(psPid, k -> new ArrayList<JSONObject>());
                childMenu.get(psPid).add(temp);
            }
        }

        // 遍历data
        for(JSONObject json: dara_array){
            ArrayList<JSONObject> tmp_array = childMenu.get(json.getInteger("id"));
            if(tmp_array != null){
                json.put("children", tmp_array);
            }

        }
        ret_json.put("data", dara_array);

        return ret_json;
    }



}
