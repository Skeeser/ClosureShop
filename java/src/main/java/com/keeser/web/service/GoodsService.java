package com.keeser.web.service;


import com.alibaba.fastjson.JSONObject;
import com.keeser.web.common.ResultCode;
import com.keeser.web.common.ResultMetaJson;
import com.keeser.web.dao.GoodsDAO;
import com.keeser.web.entity.Goods;
import com.keeser.web.entity.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


// 商品管理的服务类

@Service
public class GoodsService {
    @Autowired
    GoodsDAO  goodsDAO;

    // 获取商品列表  query是模糊搜索的词
    public JSONObject getGoodsList(String query,  int pagenum, int pagessize){
        List<Goods> goodList = null;
        int allNum = 0;
        try {
            // 模糊搜索获取数量
            allNum = goodsDAO.countAllByGoodsNameLike("%" + query + "%");
            Sort sort = Sort.by(Sort.Direction.ASC, "goodsId");
            Pageable pageable = PageRequest.of(pagenum, pagessize, sort);
            goodList = goodsDAO.findAllByGoodsNameLike("%" + query + "%", pageable);

        }catch (Exception e){
            return new ResultMetaJson(ResultCode.STATUS_BAD_REQUEST, "获取商品列表发生异常").getMetaJson();
        }

        JSONObject retJson = new ResultMetaJson(ResultCode.STATUS_OK, "获取商品列表成功").getMetaJson();
        JSONObject dataJson = new JSONObject();
        dataJson.put("total", allNum);
        dataJson.put("pagenum", pagenum);

        ArrayList<JSONObject> goodsJsonList = new ArrayList<JSONObject>();
        // 生成商品列表
        for(Goods goods : goodList){
            JSONObject goodsJson = new JSONObject();
            goodsJson.put("goods_id", goods.getGoodsId());
            goodsJson.put("goods_price", goods.getGoodsPrice());
            goodsJson.put("goods_name", goods.getGoodsName());
            goodsJson.put("goods_number", goods.getGoodsNumber());
            goodsJson.put("goods_weight", goods.getGoodsWeight());
            goodsJson.put("goods_state", goods.getGoodsState());
            goodsJson.put("add_time", goods.getAddTime());
            goodsJson.put("upd_time", goods.getUpdTime());
            goodsJson.put("hot_mumber", goods.getHotNumber());
            goodsJson.put("is_promote", goods.getIsPromote());
            goodsJsonList.add(goodsJson);
        }
        dataJson.put("goods", goodsJsonList);
        retJson.put("data", dataJson);

        return retJson;

    }

    // 获取商品信息
    public JSONObject getGoodsById(int id){
        Goods goods = null;
        try{
            // 获取商品
            goods = goodsDAO.findGoodsByGoodsId(id);

        }catch (Exception e){
            return new ResultMetaJson(ResultCode.STATUS_NOT_FOUND, "获取商品发生异常").getMetaJson();
        }

        JSONObject retJson = new ResultMetaJson(ResultCode.STATUS_OK, "获取商品成功").getMetaJson();
        JSONObject goodsJson = new JSONObject();
        goodsJson.put("goods_id", goods.getGoodsId());
        goodsJson.put("goods_price", goods.getGoodsPrice());
        goodsJson.put("goods_name", goods.getGoodsName());
        goodsJson.put("goods_number", goods.getGoodsNumber());
        goodsJson.put("goods_weight", goods.getGoodsWeight());
        goodsJson.put("goods_state", goods.getGoodsState());
        goodsJson.put("add_time", goods.getAddTime());
        goodsJson.put("upd_time", goods.getUpdTime());
        goodsJson.put("hot_mumber", goods.getHotNumber());
        goodsJson.put("is_promote", goods.getIsPromote());
        // 商品图片列表, 暂不实现
        goodsJson.put("pics", "");
        // 商品描述, 暂不实现
        goodsJson.put("attrs", "");
        retJson.put("data", goodsJson);
        return retJson;
    }

    // 修改商品
    public JSONObject editGoodsById(int id){
        Goods goods = null;
        

        try{
            // goodsDAO.save()
        }catch (Exception e){
            return new ResultMetaJson(ResultCode.STATUS_BAD_REQUEST, "修改商品发生异常").getMetaJson();
        }
        return null;
    }
}
