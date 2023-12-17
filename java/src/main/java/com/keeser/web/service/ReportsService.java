package com.keeser.web.service;


import com.alibaba.fastjson.JSONObject;
import com.keeser.web.common.ResultCode;
import com.keeser.web.common.ResultMetaJson;
import com.keeser.web.dao.GoodsDAO;
import com.keeser.web.entity.Goods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Random;

@Service
public class ReportsService {
    @Autowired
    GoodsDAO goodsDAO;

    // 将数值映射到指定范围的函数
    private double mapToRange(double value, double inMin, double inMax, double outMin, double outMax) {
        return (value - inMin) * (outMax - outMin) / (inMax - inMin) + outMin;
    }

    // 随机初始化hotnumber
    public void randomHotNumber(){
        Random random = new Random();
        double mean = 75; // 均值
        double standardDeviation = 25; // 标准差


        try{
            ArrayList<Goods> goodsArrayList = goodsDAO.findAll();

            for(Goods goods: goodsArrayList){
                // 生成正态分布的随机数
                double value = random.nextGaussian() * standardDeviation + mean;
                // 将随机数映射到 0 到 150 的范围
                double scaledValue = mapToRange(value, mean - 3 * standardDeviation, mean + 3 * standardDeviation, 0, 150);
                goods.setHotNumber((int) scaledValue);
                goodsDAO.save(goods);
            }

        }catch (Exception e){
            System.out.println("出错");
        }
    }

    // 获取热度统计
    public JSONObject getHotNumberReport(){
        ArrayList<Goods>  goodsArrayList = null;
        ArrayList<JSONObject> dataArray = new ArrayList<>();
        try{

            goodsArrayList = goodsDAO.findTop15ByOrderByHotNumberDesc();
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
