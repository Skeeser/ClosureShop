package com.keeser.web.service;


import com.alibaba.fastjson.JSONObject;
import com.keeser.web.common.ResultCode;
import com.keeser.web.common.ResultMetaJson;
import com.keeser.web.dao.GoodsDAO;
import com.keeser.web.entity.Goods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ReportsService {
    @Autowired
    GoodsDAO goodsDAO;

    // 随机初始化hotnumber
    public void randomHotNumber(){}

    // 获取热度统计
    public JSONObject getHotNumberReport(){
        ArrayList<Goods>  goodsArrayList = new ArrayList<>();
        ArrayList<JSONObject> dataArray = new ArrayList<>();
        try{

            goodsArrayList = goodsDAO.findTop10ByOrderByHotNumberDesc();
            for(Goods goods: goodsArrayList){
                JSONObject dataJson = new JSONObject();
                dataJson.put("goods_name", goods.getGoodsName());
                dataJson.put("hot_number", goods.getHotNumber());
                dataArray.add(dataJson);
            }

        }catch (Exception e){
            return new ResultMetaJson(ResultCode.STATUS_BAD_REQUEST, "获取数据报表发生异常").getMetaJson();
        }
        JSONObject ret_json = new ResultMetaJson(ResultCode.STATUS_OK, "获取数据报表成功").getMetaJson();
        ret_json.put("data", dataArray);

        return ret_json;
    }
}
