package com.keeser.web.service;


import com.alibaba.fastjson.JSONObject;
import com.keeser.web.common.ResultCode;
import com.keeser.web.common.ResultMetaJson;
import com.keeser.web.dao.OrdersDAO;
import com.keeser.web.entity.Goods;
import com.keeser.web.entity.Orders;
import com.keeser.web.entity.OrdersGoods;
import com.keeser.web.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    // 插入空的订单并返回订单号
    private  int getNewOders(int user_id){
        try{
            Orders orders = new Orders();
            orders.setUserId(user_id);
            ordersDAO.save(orders);
            return orders.getOrderId();
        }catch (Exception e){
            return 0;
        }
    }

    // 添加购物车
    public JSONObject  addOrders(JSONObject addOdersGoodsJson){

        Orders orders = null;

        try{
            // 获取当前用户信息
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User user = (User)authentication.getPrincipal();
            int user_id = user.getId();

            // 先找有没有没付款的订单
            orders = ordersDAO.findByIsCompleteOrderEquals('否');
            if(orders == null){
                // 如果没有没付款的, 即购物车为空, 新建
                int orderId = getNewOders(user_id);
                orders = ordersDAO.findByOrderId(orderId);
            }

            List<OrdersGoods> ordersGoodsList = orders.getOrdersGoodsList();
            // 计算购买的商品价格
            Double goods_price  = addOdersGoodsJson.getDouble("goods_price");
            int goods_number = addOdersGoodsJson.getInteger("buy_number");
            OrdersGoods tempOrderGoods = new OrdersGoods();
            tempOrderGoods.setGoodsId(addOdersGoodsJson.getInteger("goods_id"));
            tempOrderGoods.setGoodsPrice(goods_price);
            tempOrderGoods.setGoodsNumber(goods_number);
            tempOrderGoods.setGoodsTotalPrice(goods_price * goods_number);
            ordersGoodsList.add(tempOrderGoods);

            ordersDAO.save(orders);
        }catch (Exception e){
            return new ResultMetaJson(ResultCode.STATUS_BAD_REQUEST, "添加订单发生异常").getMetaJson();
        }

        return null;
    }

}
