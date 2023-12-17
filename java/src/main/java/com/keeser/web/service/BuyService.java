package com.keeser.web.service;


import com.alibaba.fastjson.JSONObject;
import com.keeser.web.common.ResultCode;
import com.keeser.web.common.ResultMetaJson;
import com.keeser.web.dao.OrdersDAO;
import com.keeser.web.entity.Goods;
import com.keeser.web.entity.Orders;
import com.keeser.web.entity.OrdersGoods;
import com.keeser.web.entity.User;
import jakarta.json.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class BuyService {

    @Autowired
    OrdersDAO ordersDAO;

    public JSONObject payForOrder(JSONObject payJson){
        Orders orders = null;

        JSONObject dataJson = new JSONObject();
        try{
           // 获取order id
            int order_id = payJson.getInteger("order_id");
            // 获取order对象
            orders = ordersDAO.findByOrderId(order_id);

            dataJson.put("order_id", orders.getOrderId());
            // 更新订单信息, 设置已经付款
            orders.setPayStatus('1');
            // 更新别的信息
            // 设置订单结算
            orders.setIsCompleteOrder('是');
            // 设置更新时间
            orders.setUpdateTime(new Date().getTime() / 1000 );
            // 保存
            ordersDAO.save(orders);

        }catch (Exception e){
            return new ResultMetaJson(ResultCode.STATUS_BAD_REQUEST, "支付发生异常").getMetaJson();
        }

        JSONObject retJson = new ResultMetaJson(ResultCode.STATUS_OK, "支付成功").getMetaJson();
        retJson.put("data", dataJson);
        return retJson;
    }
}
