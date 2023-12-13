package com.keeser.web.service;


import com.alibaba.fastjson.JSONObject;
import com.keeser.web.dao.GoodsDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class GoodsService {
    @Autowired
    GoodsDAO  goodsDAO;

    // 获取商品列表  query是模糊搜索的词
    public JSONObject getGoodsList(String query,  int pagenum, int pagessize){
        int page_num = -1;
        int page_size = -1;


        // 排序的参数
        String sort_prop = "";
        // 排序的顺序
        String sort_order = "";

        return null;

    }

}
