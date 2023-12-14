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
import java.util.Date;
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
            Sort sort = Sort.by(Sort.Direction.DESC, "goodsId");
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
            goods = goodsDAO.findByGoodsId(id);

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
    public JSONObject editGoodsById(int id, JSONObject editGoods){

        Goods goods = null;

        try{
            // 先取goods对象
            goods = goodsDAO.findByGoodsId(id);
            // 修改对象
            goods.setGoodsName(editGoods.getString("goods_name"));
            goods.setGoodsPrice(editGoods.getDouble("goods_price"));
            goods.setGoodsNumber(editGoods.getInteger("goods_number"));
            goods.setGoodsWeight(editGoods.getInteger("goods_weight"));
            // 修改时间
            goods.setUpdTime(new Date().getTime() / 1000);
            goodsDAO.save(goods);
        }catch (Exception e){
            return new ResultMetaJson(ResultCode.STATUS_BAD_REQUEST, "修改商品发生异常").getMetaJson();
        }
        JSONObject retJson = getGoodsById(id);
        JSONObject metaJson = (JSONObject)retJson.get("meta");
        metaJson.put("msg", "修改商品成功");
        metaJson.put("status", ResultCode.STATUS_OK);
        return retJson;
    }

    public JSONObject addGoods(JSONObject addJson){
        Goods goods = new Goods();
        try {
            goods.setGoodsName(addJson.getString("goods_name"));
            goods.setGoodsPrice(addJson.getDouble("goods_price"));
            goods.setGoodsNumber(addJson.getInteger("goods_number"));
            goods.setGoodsWeight(addJson.getInteger("goods_weight"));
            goods.setAddTime(new Date().getTime() / 1000);
            goods.setGoodsState('0');
            goods.setGoodsIntroduce(addJson.getString("goods_introduce"));
            goods = goodsDAO.save(goods);
        }catch (Exception e){
            return new ResultMetaJson(ResultCode.STATUS_BAD_REQUEST , "添加商品发生异常").getMetaJson();
        }
        JSONObject retJson = getGoodsById(goods.getGoodsId());
        JSONObject metaJson = (JSONObject)retJson.get("meta");
        metaJson.put("msg", "添加商品成功");
        metaJson.put("status", ResultCode.STATUS_CREATED);
        return retJson;
    }
}
