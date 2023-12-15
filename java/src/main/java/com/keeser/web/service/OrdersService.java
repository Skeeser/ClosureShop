package com.keeser.web.service;


import com.alibaba.fastjson.JSONObject;
import com.keeser.web.common.ResultCode;
import com.keeser.web.common.ResultMetaJson;
import com.keeser.web.dao.OrdersDAO;
import com.keeser.web.entity.Goods;
import com.keeser.web.entity.Orders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrdersService {
    @Autowired
    OrdersDAO ordersDAO;

    // 获取订单列表
    public JSONObject getOrdersList(String query,  int pagenum, int pagessize){
        List<Orders> ordersList = null;

        int allNum = 0;
        try {
            // 模糊搜索获取数量
            allNum = ordersDAO.countAllByOrderNumberLike("%" + query + "%");
            Sort sort = Sort.by(Sort.Direction.DESC, "orderNumber");
            // 要减一保证从零开始
            Pageable pageable = PageRequest.of(pagenum - 1, pagessize, sort);
            ordersList = ordersDAO.findAllByOrderNumberLike("%" + query + "%", pageable);

        }catch (Exception e){
            return new ResultMetaJson(ResultCode.STATUS_BAD_REQUEST, "获取订单列表发生异常").getMetaJson();
        }

        JSONObject retJson = new ResultMetaJson(ResultCode.STATUS_OK, "获取订单列表成功").getMetaJson();
        JSONObject dataJson = new JSONObject();
        dataJson.put("total", allNum);
        dataJson.put("pagenum", pagenum);

        ArrayList<JSONObject> ordersJsonList = new ArrayList<JSONObject>();
        // 生成商品列表
        for(Orders order : ordersList){
            JSONObject orderJson = new JSONObject();
            orderJson.put("order_id", order.getOrderId());
            orderJson.put("user_id", order.getUserId());
            orderJson.put("order_number", order.getOrderNumber());
            orderJson.put("order_price", order.getOrderPrice());
            orderJson.put("order_pay", order.getOrderPay());
            orderJson.put("is_send", order.getIsSend());
            orderJson.put("trade_no", order.getTradeNo());
            orderJson.put("order_fapiao_title", order.getOrderFapiaoTitle());
            orderJson.put("order_fapiao_company", order.getOrderFapiaoCompany());
            orderJson.put("order_fapiao_content", order.getOrderFapiaoContent());
            orderJson.put("consignee_addr", order.getConsigneeAddr());
            orderJson.put("pay_status", order.getPayStatus());
            orderJson.put("create_time", order.getCreateTime());
            orderJson.put("update_time", order.getUpdateTime());
            ordersJsonList.add(orderJson);
        }
        dataJson.put("goods", ordersJsonList);
        retJson.put("data", dataJson);

        return retJson;

    }

    // 添加订单
    public JSONObject  addOrders(JSONObject addOdersJson){

        Orders orders = new Orders();

        try{
            orders.getOrdersGoods().setGoodsId(addOdersJson.getInteger("goods_id"));
        }catch (Exception e){
            return new ResultMetaJson(ResultCode.STATUS_BAD_REQUEST, "添加订单发生异常").getMetaJson();
        }
    }

}
