package com.keeser.web.service;


import com.alibaba.fastjson.JSONObject;
import com.keeser.web.common.ResultCode;
import com.keeser.web.common.ResultMetaJson;
import com.keeser.web.dao.PermissionApiDAO;
import com.keeser.web.dao.PermissionDAO;
import com.keeser.web.entity.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


// 菜单service

@Service
public class MenusService {
    @Autowired
    PermissionDAO permissionDAO;

    // 将取到的数据进行嵌套处理, 返回菜单json
    public JSONObject getMenus(){
        // 获取菜单
        List<Permission> menus = this.permissionDAO.findAll();

        JSONObject ret_json = new ResultMetaJson(ResultCode.STATUS_OK, "获取菜单列表成功").getMetaJson();

        return ret_json;
    }



}
