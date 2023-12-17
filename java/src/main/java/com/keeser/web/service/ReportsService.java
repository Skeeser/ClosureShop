package com.keeser.web.service;


import com.alibaba.fastjson.JSONObject;
import com.keeser.web.dao.GoodsDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportsService {
    @Autowired
    GoodsDAO goodsDAO;

    public JSONObject getHotNumberReport(){
        return null;
    }
}
